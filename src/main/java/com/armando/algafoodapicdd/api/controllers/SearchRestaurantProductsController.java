package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.response.ProductResponse;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

// Carga: 4; limite: 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class SearchRestaurantProductsController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // +1
    public List<ProductResponse> list(@PathVariable Long restaurantId) {
        // +1
        Restaurant restaurant = findRestaurantOrFail(restaurantId);
        return restaurant.getProducts().stream()
                // +1
                .map(product -> new ProductResponse(product))
                .collect(Collectors.toList());
    }

    private Restaurant findRestaurantOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // +1
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

}
