package com.armando.algafoodapicdd.api.helpers;

import com.armando.algafoodapicdd.domain.model.PaymentMethod;
import com.armando.algafoodapicdd.domain.model.Restaurant;

import java.util.Optional;

// Carga intrínsica = 3; Limite = 7
public abstract class RestaurantPaymentMethodHelper {

    // Carga: +1 (Restaurant)
    public static Boolean existsInRestaurant(Restaurant restaurant, Long paymentMethodId) {
        // Carga: +1 (PaymentMethod)
        Optional<PaymentMethod> paymentMethodFound = restaurant.getPaymentMethods().stream()
                // Carga: +1 (função como argumento no filter)
                .filter(paymentMethod -> paymentMethod.getId() == paymentMethodId)
                .findAny();
        return paymentMethodFound.isPresent();
    }

}
