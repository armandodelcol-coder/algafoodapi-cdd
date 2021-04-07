package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.RestaurantRequest;
import com.armando.algafoodapicdd.api.model.response.RestaurantResponse;
import com.armando.algafoodapicdd.api.model.response.RestaurantSummaryResponse;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class CrudRestaurantsController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public RestaurantResponse insert(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequest.toModel(manager);
        manager.persist(restaurant);
        return new RestaurantResponse(restaurant);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantSummaryResponse> list() {
        return manager
                .createQuery("SELECT restaurant from Restaurant restaurant", Restaurant.class)
                .getResultList()
                .stream().map(restaurant -> new RestaurantSummaryResponse(restaurant))
                .collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantResponse findById(@PathVariable Long restaurantId) {
        Restaurant restaurant = manager.find(Restaurant.class, restaurantId);
        if (restaurant == null) throw new EntityNotFoundException("Restaurante não encontrado");
        return new RestaurantResponse(restaurant);
    }

}
