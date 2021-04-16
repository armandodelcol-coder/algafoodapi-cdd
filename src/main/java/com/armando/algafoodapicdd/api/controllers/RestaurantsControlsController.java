package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/restaurants/{restaurantId}")
public class RestaurantsControlsController {

    @PersistenceContext
    private EntityManager manager;

    @PutMapping("/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void activate(@PathVariable Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.activate();
        manager.persist(restaurant);
    }

    @PutMapping("/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deactivate(@PathVariable Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.deactivate();
        manager.persist(restaurant);
    }

    @PutMapping("/openup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void openUp(@PathVariable Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.openUp();
        manager.persist(restaurant);
    }

    @PutMapping("/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void close(@PathVariable Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.close();
        manager.persist(restaurant);
    }

    private Restaurant findOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

}
