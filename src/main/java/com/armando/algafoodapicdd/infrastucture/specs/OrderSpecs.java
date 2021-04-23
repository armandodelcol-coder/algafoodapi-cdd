package com.armando.algafoodapicdd.infrastucture.specs;

import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.repository.filters.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.fetch("restaurant").fetch("kitchen");
            root.fetch("client");

            var predicates = new ArrayList<Predicate>();

            if (filter.getClientId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreatedAtBegin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtBegin()));
            }

            if (filter.getCreatedAtEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtEnd()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}