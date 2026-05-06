package com.github.diogenesssantos.facilittecnologia.repository;

import com.github.diogenesssantos.facilittecnologia.model.Status;
import com.github.diogenesssantos.facilittecnologia.model.Tarefa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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

    @Override
    public Optional<Tarefa> buscarPorTituloEDescricao(String titulo, String descricao) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Tarefa.class);
        var root = query.from(Tarefa.class);
        query.select(root);

        List<Predicate> predicates = List.of(
                builder.like(root.get("titulo"), titulo),
                builder.like(root.get("descricao"), descricao));

        query.where(predicates);

        var typedQuery = entityManager.createQuery(query);
        List<Tarefa> tarefa = typedQuery.getResultList();
        return !tarefa.isEmpty() ? Optional.of(tarefa.getFirst()) : Optional.empty();
    }


}
