package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.response.PaymentMethodResponse;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 4; Limite = 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/paymentmethods")
public class ListRestaurantPaymentMethodsController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Carga: +1 (PaymentMethodResponse)
    public List<PaymentMethodResponse> list(@PathVariable Long restaurantId) {
        // Carga: +1 (Restaurant)
        Restaurant restaurant = findRestaurantOrFail(restaurantId);
        return restaurant.getPaymentMethods()
                // Carga: +1 (função como argumento)
                .stream().map(paymentMethod -> new PaymentMethodResponse(paymentMethod))
                .collect(Collectors.toList());
    }

    private Restaurant findRestaurantOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

}
