package com.github.diogenesssantos.facilittecnologia.controller;

import com.github.diogenesssantos.facilittecnologia.configuration.security.JwtUtil;
import com.github.diogenesssantos.facilittecnologia.docs.LoginDocumentacaoOpenAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class LoginController implements LoginDocumentacaoOpenAPI {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public static class AuthRequest { public String nome; public String senha; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        var token = new UsernamePasswordAuthenticationToken(req.nome, req.senha);
        Authentication auth = authenticationManager.authenticate(token);

        var roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        String jwt = jwtUtil.generateToken(auth.getName(), roles);

        return ResponseEntity.ok(Map.of("token", jwt, "user", auth.getName()));
    }
}


