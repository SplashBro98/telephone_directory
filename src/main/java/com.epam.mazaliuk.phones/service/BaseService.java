package com.epam.mazaliuk.phones.service;

import com.epam.mazaliuk.phones.search.BaseSearch;

import java.util.List;

public interface BaseService<T, V extends BaseSearch, ID> {


    T find(ID id);

    T save(T t);

    void delete(T t);

    void deleteById(ID id);

    List<T> find(V v, int offset, int limit);

}
