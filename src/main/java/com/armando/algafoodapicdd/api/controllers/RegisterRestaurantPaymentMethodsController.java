package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.PaymentMethodAssociationRequest;
import com.armando.algafoodapicdd.api.utils.RestaurantPaymentMethodUtil;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/paymentmethods")
public class RegisterRestaurantPaymentMethodsController {

    @PersistenceContext
    private EntityManager manager;

    @PutMapping("/associate")
    @Transactional
    // Carga: +1 (AssociatePaymentMethodRequest)
    public ResponseEntity<?> associatePaymentMethod(
            @PathVariable Long restaurantId,
            @RequestBody @Valid PaymentMethodAssociationRequest request
    ) {
        // Carga: +1 (Restaurant)
        Restaurant restaurant = findRestaurantOrFail(restaurantId);
        // Carga: +1 (RestaurantPaymentMethodHelper); +1 (branch if)
        if (RestaurantPaymentMethodUtil.existsInRestaurant(restaurant, request.getPaymentMethodId())) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (ErrorResponseBodyHelper)
                    ErrorResponseBodyHelper.badRequest("Já existe essa forma de pagamento associada nesse restaurante.")
            );
        }

        restaurant.associatePaymentMethod(manager.find(PaymentMethod.class, request.getPaymentMethodId()));
        manager.persist(restaurant);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/dissociate")
    @Transactional
    public ResponseEntity<?> dissociatePaymentMethod(
            @PathVariable Long restaurantId,
            @RequestBody @Valid PaymentMethodAssociationRequest request
    ) {
        Restaurant restaurant = findRestaurantOrFail(restaurantId);
        // Carga: +1 (branch if)
        if (!RestaurantPaymentMethodUtil.existsInRestaurant(restaurant, request.getPaymentMethodId())) {
            return ResponseEntity.badRequest().body(
                    ErrorResponseBodyHelper.badRequest("A forma de pagamento informada não está associada nesse restaurante.")
            );
        }

        restaurant.dissociatePaymentMethod(manager.find(PaymentMethod.class, request.getPaymentMethodId()));
        manager.persist(restaurant);
        return ResponseEntity.ok().build();
    }

    private Restaurant findRestaurantOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

}
