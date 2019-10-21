package com.epam.mazaliuk.phones.specification.user;

import com.epam.mazaliuk.phones.entity.UserEntity;
import com.epam.mazaliuk.phones.specification.BaseSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFindByCitySpecification implements BaseSpecification<UserEntity> {

    private static final String CITY = "city";

    private String city;

    @Override
    public Predicate getPredicate(Root<UserEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(CITY), city);
    }
}
