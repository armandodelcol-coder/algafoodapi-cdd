package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.PaymentMethod;

// Carga intrínsica = 1; Limite = 7
public class PaymentMethodResponse {

    private Long id;
    private String description;

    // Carga: +1 (PaymentMethod)
    public PaymentMethodResponse(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
