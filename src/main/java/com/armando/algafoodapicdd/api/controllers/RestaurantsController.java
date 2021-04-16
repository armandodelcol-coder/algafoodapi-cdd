package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.request.RestaurantRequest;
import com.armando.algafoodapicdd.api.model.response.RestaurantResponse;
import com.armando.algafoodapicdd.api.model.response.RestaurantSummaryResponse;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 6; Limite = 7
@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (RestaurantResponse) +1 (RestaurantRequest)
    public RestaurantResponse insert(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        // Carga: +1 (Restaurant)
        Restaurant restaurant = restaurantRequest.toModel(manager);
        manager.persist(restaurant);
        return new RestaurantResponse(restaurant);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Carga: +1 (RestaurantSummaryResponse)
    public List<RestaurantSummaryResponse> list() {
        return manager
                .createQuery("SELECT restaurant from Restaurant restaurant", Restaurant.class)
                .getResultList()
                // Carga: +1 (função como argumento no map)
                .stream().map(restaurant -> new RestaurantSummaryResponse(restaurant))
                .collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantResponse findById(@PathVariable Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        return new RestaurantResponse(restaurant);
    }

    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public RestaurantResponse update(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantRequest restaurantRequest
    ) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.setPropertiesToUpdate(restaurantRequest, manager);
        manager.persist(restaurant);
        return new RestaurantResponse(restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        manager.remove(restaurant);
    }

    private Restaurant findOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

}
