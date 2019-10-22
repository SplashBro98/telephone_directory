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
public class UserFindByUserNameSpecification implements BaseSpecification<UserEntity> {

    private static final String USER_NAME = "userName";

    private String userName;

    @Override
    public Predicate getPredicate(Root<UserEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(USER_NAME), userName);
    }
}
