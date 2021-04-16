package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.response.UserResponse;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 4; Limite = 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/responsible")
public class ListRestaurantResponsibleController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Carga: +1 (UserResponse)
    public List<UserResponse> list(@PathVariable Long restaurantId) {
        // Carga: +1 (Restaurant)
        Restaurant restaurant = manager.find(Restaurant.class, restaurantId);
        // Carga: +1 (EntityNotFoundVerificationHelper)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant.getResponsible().stream()
                // Carga: +1 (função como argumento)
                .map(user -> new UserResponse(user))
                .collect(Collectors.toList());
    }

}
