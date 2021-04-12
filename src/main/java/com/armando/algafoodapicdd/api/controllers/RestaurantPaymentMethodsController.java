package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.utils.RestaurantPaymentMethodUtil;
import com.armando.algafoodapicdd.api.model.request.PaymentMethodAssociationRequest;
import com.armando.algafoodapicdd.api.model.response.PaymentMethodResponse;
import com.armando.algafoodapicdd.api.utils.EntityNotFoundVerification;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 9; Limite = 7
@RestController
@RequestMapping("/restaurants/{restaurantId}/paymentmethods")
public class RestaurantPaymentMethodsController {

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

    @PutMapping("/associate")
    @Transactional
    // Carga: +1 (AssociatePaymentMethodRequest)
    public ResponseEntity<?> associatePaymentMethod(
            @PathVariable Long restaurantId,
            @RequestBody @Valid PaymentMethodAssociationRequest request
    ) {
        Restaurant restaurant = findRestaurantOrFail(restaurantId);
        // Carga: +1 (RestaurantPaymentMethodHelper); +1 (branch if)
        if (RestaurantPaymentMethodUtil.existsInRestaurant(restaurant, request.getPaymentMethodId())) {
            return ResponseEntity.badRequest().body(
                    badRequestResponseBody("Já existe essa forma de pagamento associada nesse restaurante.")
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
                    badRequestResponseBody("A forma de pagamento informada não está associada nesse restaurante.")
            );
        }

        restaurant.dissociatePaymentMethod(manager.find(PaymentMethod.class, request.getPaymentMethodId()));
        manager.persist(restaurant);
        return ResponseEntity.ok().build();
    }

    private Restaurant findRestaurantOrFail(Long id) {
        Restaurant restaurant = manager.find(Restaurant.class, id);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerification.dispatchIfEntityIsNull(restaurant, "Restaurante não encontrado.");
        return restaurant;
    }

    private CustomErrorResponseBody badRequestResponseBody(String message) {
        // Carga: +1 (CustomErrorResponseBody)
        return new CustomErrorResponseBody(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                OffsetDateTime.now()
        );
    }

}
