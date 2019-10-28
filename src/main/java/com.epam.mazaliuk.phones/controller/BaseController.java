package com.epam.mazaliuk.phones.controller;

import com.epam.mazaliuk.phones.search.BaseSearch;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Component
public abstract class BaseController<V extends BaseSearch> {

    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }

    public abstract ModelAndView findAll(V baseSearch, Integer offset, Integer limit);

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
