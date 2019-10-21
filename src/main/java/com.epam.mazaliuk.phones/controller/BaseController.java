package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.search.BaseSearch;

import java.util.List;

public abstract class BaseController<T, V extends BaseSearch> {

    public abstract List<T> findAll(V baseSearch, Integer offset, Integer limit);

    Integer validateOffset(Integer offset) {
        if (offset == null || offset < 0) {
            return 0;
        }

        return offset;
    }

    Integer validateLimit(Integer limit) {
        if (limit == null || limit < 0 || limit > 100000) {
            return 10;
        }

        return limit;
    }

}
