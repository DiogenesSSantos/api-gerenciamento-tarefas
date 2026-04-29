


CREATE TABLE tb_tarefa(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(250) NOT NULL,
    descricao TEXT NOT NULL,
    t_status VARCHAR(15) NOT NULL,
    responsavel VARCHAR(150) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    data_limite TIMESTAMP NOT NULL
);