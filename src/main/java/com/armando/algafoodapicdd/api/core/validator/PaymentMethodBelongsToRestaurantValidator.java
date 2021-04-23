package com.armando.algafoodapicdd.api.core.validator;

import com.armando.algafoodapicdd.api.model.request.OrderRequest;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class PaymentMethodBelongsToRestaurantValidator implements Validator {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // if (errors.hasErrors()) return;

        OrderRequest request = (OrderRequest) target;
        Restaurant restaurant = manager.find(Restaurant.class, request.getRestaurantId());
        PaymentMethod paymentMethod = manager.find(PaymentMethod.class, request.getPaymentMethodId());
        if (restaurant.acceptPaymentMethod(paymentMethod)) return;

        errors.rejectValue("paymentMethodId", null, "O restaurante não aceita o método de pagamento informado.");
    }

}
