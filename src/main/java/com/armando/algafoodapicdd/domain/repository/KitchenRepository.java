package com.armando.algafoodapicdd.domain.repository;

import com.armando.algafoodapicdd.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenRepository extends JpaRepository<Kitchen, Long> {
}
