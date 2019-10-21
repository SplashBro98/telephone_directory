package com.epam.mazaliuk.phones.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface BaseSpecification<T> {

    static BaseSpecification empty() {
        return (root, cb) -> null;
    }

    Predicate getPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);

    default BaseSpecification<T> and(BaseSpecification<T> specification) {

        return (root, criteriaBuilder) -> {
            Predicate initialPredicate = this.getPredicate(root, criteriaBuilder);
            Predicate anotherPredicate = specification.getPredicate(root, criteriaBuilder);

            if (initialPredicate == null) {
                return anotherPredicate;
            } else if (anotherPredicate == null) {
                return initialPredicate;
            }

            return criteriaBuilder.and(initialPredicate, anotherPredicate);

        };
    }

    default BaseSpecification<T> or(BaseSpecification<T> specification) {

        return (root, criteriaBuilder) -> {
            Predicate initialPredicate = this.getPredicate(root, criteriaBuilder);
            Predicate anotherPredicate = specification.getPredicate(root, criteriaBuilder);

            if (initialPredicate == null) {
                return anotherPredicate;
            } else if (anotherPredicate == null) {
                return initialPredicate;
            }

            return criteriaBuilder.or(initialPredicate, anotherPredicate);

        };
    }
}
