package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.RestaurantResponsibleRequest;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/responsible")
public class RegisterRestaurantResponsibleController {

    @PersistenceContext
    private EntityManager manager;

    @PutMapping("/associate")
    @Transactional
    // Carga: +1 (RestaurantResponsibleRequest);
    public ResponseEntity<?> associate(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantResponsibleRequest request
    ) {
        // Carga: +1 (Restaurant)
        Restaurant restaurant = findUserOrFail(restaurantId);
        // Carga: +1 (branch if);
        if (restaurant.hasThisResponsibleById(request.getUserId())) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (ErrorResponseBodyHelper);
                    ErrorResponseBodyHelper.badRequest("Esse usuário já é um responsável desse restaurante.")
            );
        }

        // Carga: +1 (User);
        User user = manager.find(User.class, request.getUserId());
        restaurant.associateResponsible(user);
        manager.persist(restaurant);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/dissociate")
    @Transactional
    public ResponseEntity<?> dissociate(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantResponsibleRequest request
    ) {
        Restaurant restaurant = findUserOrFail(restaurantId);
        // Carga: +1 (branch if);
        if (restaurant.hasThisResponsibleById(request.getUserId())) {
            User user = manager.find(User.class, request.getUserId());
            restaurant.dissociateResponsible(user);
            manager.persist(restaurant);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body(
                ErrorResponseBodyHelper.badRequest("Esse usuário não é um responsável desse restaurante.")
        );
    }

    private Restaurant findUserOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // Carga: +1 (EntityNotFoundVerificationHelper);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado");
        return restaurant;
    }

}
