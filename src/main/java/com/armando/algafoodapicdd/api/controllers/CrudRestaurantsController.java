package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.RestaurantRequest;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/restaurants")
public class CrudRestaurantsController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public String insert(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequest.toModel(manager);
        manager.persist(restaurant);
        return restaurant.toString();
    }

}
