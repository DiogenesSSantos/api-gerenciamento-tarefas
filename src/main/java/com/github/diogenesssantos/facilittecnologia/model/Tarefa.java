package com.github.diogenesssantos.facilittecnologia.model;

import com.github.diogenesssantos.facilittecnologia.exception.BuilderTarefaException;
import com.github.diogenesssantos.facilittecnologia.exception.CampoInvalidoException;
import com.github.diogenesssantos.facilittecnologia.util.ValidaHoraUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime ;

@Data
@Entity
@Table(name = "tb_tarefa")
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

    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime  dataCriacao;

    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime  dataAtualizacao;

    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime  dataLimite;


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
     *                 .dataCriacao(LocalDateTime .now())
     *                 .dataAtualizacao(LocalDateTime .now())
     *                 .dataLimite(LocalDateTime .now().plus(2, ChronoUnit.DAYS))
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
    private Tarefa(String titulo, String descricao, String responsavel, Status status, LocalDateTime  dataCriacao,
                   LocalDateTime  dataAtualizacao, LocalDateTime  dataLimite) {
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
//               .atZoneSameLocalDateTime (ZoneId.of("America/Sao_Paulo"))
//               .toOffsetDateTime();
//    }
//


    public static class Builder {
        private String titulo;
        private String descricao;
        private String responsavel;
        private Status status;
        private LocalDateTime  dataCriacao;
        private LocalDateTime  dataAtualizacao;
        private LocalDateTime  dataLimite;

        public Builder() {
        }

        public Builder titulo(String titulo) {
            if (titulo == null || titulo.isBlank()) {
                throw new CampoInvalidoException("O campo titulo não poder ser vázio", "titulo");
            }

            this.titulo = titulo;
            return this;
        }


        public Builder descricao(String descricao) {
            if (descricao == null || descricao.isBlank()) {
                throw new CampoInvalidoException("O campo descrição não poder ser vázio", "descricao");
            }

            this.descricao = descricao;
            return this;
        }

        public Builder responsavel(String responsavel) {
            if (responsavel == null || responsavel.isBlank()) {
                throw new CampoInvalidoException("O campo responsável não poder ser vázio", "responsavel");
            }

            this.responsavel = responsavel;
            return this;
        }


        public Builder status(Status status) {
            if (status == null) {
                throw new CampoInvalidoException(
                        "O campo status deve conter um Status (FAZER, PROGRESSO, ATRASADO, CONCLUIDO)", "status");
            }

            this.status = status;
            return this;
        }

        public Builder dataCriacao(LocalDateTime  dataCriacao) {
            if (dataCriacao == null) {
                throw new CampoInvalidoException("A data de criação tem que ser um periodo no tempo atual.", "dataCriacao");
            }

            this.dataCriacao = dataCriacao;
            return this;
        }

        public Builder dataAtualizacao(LocalDateTime  dataAtualizacao) {
            if (dataAtualizacao == null) {
                throw new CampoInvalidoException("A data de criação tem que ser em um periodo no tempo atual.", "dataAtualizacao");
            }

            this.dataAtualizacao = dataAtualizacao;
            return this;
        }


        public Builder dataLimite(LocalDateTime  dataLimite) {
            if (dataLimite == null) {
                throw new CampoInvalidoException("O campo dataLimite deve ser em um periodo no tempo futuro.", "dataLimite");
            } else if (ValidaHoraUtil.isPassado(dataLimite))
                throw new CampoInvalidoException("O campo dataLimite deve ser em um periodo no tempo futuro.", "dataLimite");

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



