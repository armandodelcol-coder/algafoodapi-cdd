package com.armando.algafoodapicdd.domain.repository;

import com.armando.algafoodapicdd.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndRestaurantId(Long productId, Long restaurantId);

}
