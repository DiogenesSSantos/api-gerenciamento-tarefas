-- V3__criando_tabela_usuario_e_roles.sql
CREATE TABLE tb_usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  is_ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role VARCHAR(50) NOT NULL,
  CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role),
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES tb_usuario(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_roles_role ON user_roles(role);

INSERT INTO tb_usuario (id, nome, senha, is_ativo) VALUES
  (1, 'facilit', '$2a$12$zgqd6Cn0Q0srZvEaShxITOA5jZa2oX3O/vv0d/wwjjphi8fEG8E06', TRUE),
  (2, 'usuario', '$2a$12$zgqd6Cn0Q0srZvEaShxITOA5jZa2oX3O/vv0d/wwjjphi8fEG8E06', TRUE);

INSERT INTO user_roles (user_id, role) VALUES
  (1, 'ROLE_ADMINISTRADOR'),
  (1, 'ROLE_USUARIO'),
  (2, 'ROLE_USUARIO');
