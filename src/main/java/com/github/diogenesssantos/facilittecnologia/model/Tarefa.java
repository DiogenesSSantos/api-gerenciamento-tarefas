package com.github.diogenesssantos.facilittecnologia.model;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.BuilderTarefaException;
import com.github.diogenesssantos.facilittecnologia.util.ValidaHoraUtil;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Entity
@Table(schema = "tb_tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "t_status")
    private Status status;

    @CreationTimestamp
    private Instant dataCriacao;

    @UpdateTimestamp
    private Instant dataAtualizacao;

    private Instant dataLimite;


    private Tarefa() {
    }

    private Tarefa(String titulo, String descricao, String responsavel, Status status, Instant dataCriacao,
                   Instant dataAtualizacao, Instant dataLimite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataLimite = dataLimite;
    }


    /*
    Refatorar para uma classe utilitária.
 */
//    public  OffsetDateTime getDataCriacao(){
//       return dataCriacao.atOffset(ZoneOffset.UTC)
//               .atZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
//               .toOffsetDateTime();
//    }
//


    public static class Builder {
        private String titulo;
        private String descricao;
        private String responsavel;
        private Status status;
        private Instant dataCriacao;
        private Instant dataAtualizacao;
        private Instant dataLimite;

        public Builder() {
        }


        public Builder titulo(String titulo) {
            if (titulo.isBlank()) {
                throw new IllegalArgumentException("O campo titulo não poder ser vázio");
            }
            return this;
        }


        public Builder descricao(String descricao) {
            if (descricao.isBlank()) {
                throw new IllegalArgumentException("O campo descrição não poder ser vázio");
            }
            return this;
        }

        public Builder responsavel(String responsavel) {
            if (responsavel.isBlank()) {
                throw new IllegalArgumentException("O campo responsável não poder ser vázio");
            }
            return this;
        }


        public Builder status(Status status) {
            if (status == null) {
                throw new IllegalArgumentException(
                        "O campo status deve conter um Status (FAZER, PROGRESSO, ATRASADO, CONCLUIDO)");
            }
            return this;
        }

        public Builder dataCriacao(Instant dataCriacao) {
            if (dataCriacao == null)
                throw new IllegalArgumentException("A data de criação tem que ser um periodo no tempo atual.");
            return this;
        }

        public Builder dataAtualizacao(Instant dataAtualizacao) {
            if (dataAtualizacao == null)
                throw new IllegalArgumentException("A data de criação tem que ser em um periodo no tempo atual.");
            return this;
        }


        public Builder dataLimite(Instant dataLimite) {
            if (ValidaHoraUtil.isPassado(dataLimite)) {
                throw new IllegalArgumentException("O campo dataLimite deve ser em um periodo no tempo futuro.");
            }

            return this;
        }


        public Tarefa build() {
            if (this.titulo == null) throw new BuilderTarefaException("O campo titulo não pode ser nulo");
            if (this.descricao == null) throw new BuilderTarefaException("O campo descrição não pode ser nulo");
            if (this.responsavel == null) throw new BuilderTarefaException("O campo responsável não pode ser nulo");
            if (this.status == null) throw new BuilderTarefaException("O campo status não pode ser nulo");
            if (this.dataCriacao == null) throw new BuilderTarefaException("O data criação não pode ser nulo");
            if (this.dataAtualizacao == null)
                throw new BuilderTarefaException("O campo data atualização não pode ser nulo");
            if (this.dataLimite == null) throw new BuilderTarefaException("O campo data limite não pode ser nulo");

            return new Tarefa(this.titulo,
                    this.descricao,
                    this.responsavel,
                    this.status,
                    this.dataCriacao,
                    this.dataAtualizacao,
                    this.dataLimite);
        }


    }


}



