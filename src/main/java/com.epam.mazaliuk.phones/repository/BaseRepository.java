package com.epam.mazaliuk.phones.repository;


import com.epam.mazaliuk.phones.specification.BaseSpecification;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    Optional<T> findById(ID id);

    Optional<T> getReferenceById(ID id);

    T save(T entity);

    void delete(T entity);

    Optional<T> findSingle(BaseSpecification<T> specification);

    List<T> find(BaseSpecification<T> specification);

    List<T> find(BaseSpecification<T> specification, int offset, int limit);

}
