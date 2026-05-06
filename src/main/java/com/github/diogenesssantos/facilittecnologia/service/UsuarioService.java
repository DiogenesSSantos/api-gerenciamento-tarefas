package com.github.diogenesssantos.facilittecnologia.service;

import com.github.diogenesssantos.facilittecnologia.model.RoleName;
import com.github.diogenesssantos.facilittecnologia.model.Usuario;
import com.github.diogenesssantos.facilittecnologia.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.usuarioRepository = repo;
        this.encoder = encoder;
    }

    public Usuario register(String nome, String senha, Set<RoleName> roles) {

        if (usuarioRepository.existsByNome(nome)) throw new RuntimeException("Usuário existe");

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenha(encoder.encode(senha));
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }


}
