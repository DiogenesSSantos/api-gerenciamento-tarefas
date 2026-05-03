package com.github.diogenesssantos.facilittecnologia.repository;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class TarefaRepositoryImpl implements TarefaRepositoryCustom {

    private EntityManager entityManager;

    public TarefaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tarefa> buscarPorTitulo(String titulo) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Tarefa.class);
        var root = query.from(Tarefa.class);
        query.select(root);

        query.where(builder.equal(builder.lower(root.get("titulo")), titulo.toLowerCase()));

        TypedQuery<Tarefa> typedQuery = entityManager.createQuery(query);
        var tarefa = typedQuery.getResultList();

        return !tarefa.isEmpty() ? Optional.of(tarefa.getFirst()) : Optional.empty();
    }

    @Override
    public Optional<Tarefa> buscarPorDescricao(String descricao) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Tarefa.class);
        var root = query.from(Tarefa.class);
        query.select(root);

        query.where(builder.equal(builder.lower(root.get("descricao")), descricao.toLowerCase()));

        var typedQuery = entityManager.createQuery(query);
        var tarefa = typedQuery.getResultList();

        return !tarefa.isEmpty() ? Optional.of(tarefa.getFirst()) : Optional.empty();
    }

    @Override
    public List<Tarefa> buscarPorStatus(Status status) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Tarefa.class);
        var root = query.from(Tarefa.class);
        query.select(root);

        query.where(builder.equal(root.get("status"), status));

        var typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }


}
