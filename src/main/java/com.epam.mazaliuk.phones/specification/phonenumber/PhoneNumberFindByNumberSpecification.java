package com.epam.mazaliuk.phones.specification.phonenumber;

import com.epam.mazaliuk.phones.entity.PhoneNumberEntity;
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
public class PhoneNumberFindByNumberSpecification implements BaseSpecification<PhoneNumberEntity> {

    private static final String NUMBER = "number";

    private String number;

    @Override
    public Predicate getPredicate(Root<PhoneNumberEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(NUMBER), number);
    }
}
