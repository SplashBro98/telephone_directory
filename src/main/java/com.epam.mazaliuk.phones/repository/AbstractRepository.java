package com.epam.mazaliuk.phones.repository;

import com.epam.mazaliuk.phones.specification.BaseSpecification;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T, ID> implements BaseRepository<T, ID> {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 10;

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> clazz;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<T> query;
    private Root<T> root;

    abstract Class<T> getType();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(getType(), id));
    }

    @Override
    public Optional<T> getReferenceById(ID id) {
        return Optional.ofNullable(entityManager.getReference(getType(), id));
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public Optional<T> findSingle(BaseSpecification<T> specification) {
        Predicate predicate = specification.getPredicate(root, criteriaBuilder);

        if (predicate != null) {
            query.where(specification.getPredicate(root, criteriaBuilder));
        }

        try {

            return Optional.of(entityManager.createQuery(query)
                    .getSingleResult());
        }catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> find(BaseSpecification<T> specification) {

        return find(specification, DEFAULT_OFFSET, DEFAULT_LIMIT);
    }

    @Override
    public List<T> find(BaseSpecification<T> specification, int offset, int limit) {

        Predicate predicate = specification.getPredicate(root, criteriaBuilder);
        if (predicate != null) {
            query.where(specification.getPredicate(root, criteriaBuilder));
        }

        if (limit == 0) {
            return entityManager.createQuery(query)
                    .setFirstResult(offset)
                    .getResultList();
        }

        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @PostConstruct
    private void prepareFields() {
        clazz = getType();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        query = criteriaBuilder.createQuery(clazz);
        root = query.from(clazz);
        query.select(root);
    }

}
