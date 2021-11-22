package com.imageApp.controller.searchOptions;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.MapsId;
import javax.persistence.criteria.*;

public interface SearchOptionsSpec<T> extends Specification<T> {

    public <V, W> Predicate makePredicate(From<V, T> from,
                                          CriteriaQuery<W> query,
                                          CriteriaBuilder criteriaBuilder);

    @Override
    default Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
        return makePredicate(root,query,criteriaBuilder);
    }

    default Predicate and(CriteriaBuilder criteriaBuilder, Predicate p1, Predicate p2) {
        if (ObjectUtils.isEmpty(p1)) {
            return p2;
        } else if (ObjectUtils.isEmpty(p2)) {
            return p1;
        } else {
            return criteriaBuilder.and(p1, p2);
        }
    }

    default Predicate makeDummyIfNeeded(CriteriaBuilder criteriaBuilder, Predicate p1) {
        if(!ObjectUtils.isEmpty(p1)) {
            return p1;
        } else {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
    }
}
