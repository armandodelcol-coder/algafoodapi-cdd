package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.request.ProductRequest;
import com.armando.algafoodapicdd.api.model.response.ProductResponse;
import com.armando.algafoodapicdd.domain.model.Product;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import com.armando.algafoodapicdd.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga: 7; limite: 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RegisterRestaurantProductsController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    // +1
    private ProductRepository productRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // +1
    private ProductResponse insert(
            @PathVariable Long restaurantId,
            // +1
            @RequestBody @Valid ProductRequest productRequest
    ) {
        // +1
        Restaurant restaurant = findRestaurantOrFail(restaurantId);
        // +1
        Product product = productRequest.toModel(restaurant);
        productRepository.save(product);
        return new ProductResponse(product);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(
            @PathVariable Long restaurantId,
            @PathVariable Long productId,
            @RequestBody @Valid ProductRequest productRequest
    ) {
        Product product = findProductOrFail(productId, restaurantId);
        product.setPropertiesToUpdate(productRequest);
        productRepository.save(product);
        return new ProductResponse(product);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(
            @PathVariable Long restaurantId,
            @PathVariable Long productId
    ) {
        Product product = findProductOrFail(productId, restaurantId);
        productRepository.delete(product);
    }

    private Restaurant findRestaurantOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // +1
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

    private Product findProductOrFail(Long productId, Long restaurantId) {
        // +1
        return productRepository.findByIdAndRestaurantId(productId, restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado.")
        );
    }

}
