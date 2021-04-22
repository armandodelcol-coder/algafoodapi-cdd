package com.armando.algafoodapicdd.domain.repository;

import com.armando.algafoodapicdd.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByCode(String code);

}
