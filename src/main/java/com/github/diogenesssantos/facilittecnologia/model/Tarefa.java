package com.github.diogenesssantos.facilittecnologia.model;

import com.github.diogenesssantos.facilittecnologia.exceptionhandller.exception.BuilderTarefaException;
import com.github.diogenesssantos.facilittecnologia.util.ValidaHoraUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
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


    /**
     * Construtor privado — use o método estático {@link Tarefa.Builder} para criar instâncias.
     *
     * <p>
     * Cria uma nova instância de {@code Tarefa} com os valores fornecidos.
     * Este construtor é privado; utilize {@code new Tarefa.Builder()...build()} para instanciar.
     * </p>
     *
     * <h3>Exemplo</h3>
     * <pre>
     * var tarefa = new Tarefa.Builder()
     *                 .titulo("Tarefa teste")
     *                 .descricao("Criando tarefa teste")
     *                 .responsavel("Diogenes da Silva Santos")
     *                 .status(Status.FAZER)
     *                 .dataCriacao(Instant.now())
     *                 .dataAtualizacao(Instant.now())
     *                 .dataLimite(Instant.now().plus(2, ChronoUnit.DAYS))
     *                 .build();
     * </pre>
     *
     * @param titulo          título da tarefa
     * @param descricao       descrição detalhada da tarefa
     * @param responsavel     nome do responsável pela tarefa
     * @param status          estado atual da tarefa (ex.: STATUS.FAZER, STATUS.PROGRESSO, STATUS.ATRASADO, STATUS.CONCLUIDO)
     * @param dataCriacao     instante de criação da tarefa
     * @param dataAtualizacao instante da última atualização
     * @param dataLimite      instante limite para conclusão da tarefa
     * @see Tarefa.Builder
     */
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
            if (titulo == null || titulo.isBlank()) {
                throw new IllegalArgumentException("O campo titulo não poder ser vázio");
            }

            this.titulo = titulo;
            return this;
        }


        public Builder descricao(String descricao) {
            if (descricao == null || descricao.isBlank()) {
                throw new IllegalArgumentException("O campo descrição não poder ser vázio");
            }

            this.descricao = descricao;
            return this;
        }

        public Builder responsavel(String responsavel) {
            if (responsavel == null || responsavel.isBlank()) {
                throw new IllegalArgumentException("O campo responsável não poder ser vázio");
            }

            this.responsavel = responsavel;
            return this;
        }


        public Builder status(Status status) {
            if (status == null) {
                throw new IllegalArgumentException(
                        "O campo status deve conter um Status (FAZER, PROGRESSO, ATRASADO, CONCLUIDO)");
            }

            this.status = status;
            return this;
        }

        public Builder dataCriacao(Instant dataCriacao) {
            if (dataCriacao == null) {
                throw new IllegalArgumentException("A data de criação tem que ser um periodo no tempo atual.");
            }

            this.dataCriacao = dataCriacao;
            return this;
        }

        public Builder dataAtualizacao(Instant dataAtualizacao) {
            if (dataAtualizacao == null) {
                throw new IllegalArgumentException("A data de criação tem que ser em um periodo no tempo atual.");
            }

            this.dataAtualizacao = dataAtualizacao;
            return this;
        }


        public Builder dataLimite(Instant dataLimite) {
            if (dataLimite == null) {
                throw new IllegalArgumentException("O campo dataLimite deve ser em um periodo no tempo futuro.");
            } else if (ValidaHoraUtil.isPassado(dataLimite))
                throw new IllegalArgumentException("O campo dataLimite deve ser em um periodo no tempo futuro.");

            this.dataLimite = dataLimite;
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



