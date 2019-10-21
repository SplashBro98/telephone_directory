package com.epam.mazaliuk.phones.specification.phonecompany;

import com.epam.mazaliuk.phones.entity.PhoneCompanyEntity;
import com.epam.mazaliuk.phones.specification.BaseSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneCompanyFindByYearSpecification implements BaseSpecification<PhoneCompanyEntity> {

    private static final String YEAR_OF_ISSUE = "yearOfIssue";

    private int yearOfIssue;

    @Override
    public Predicate getPredicate(Root<PhoneCompanyEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(YEAR_OF_ISSUE), yearOfIssue);
    }
}
