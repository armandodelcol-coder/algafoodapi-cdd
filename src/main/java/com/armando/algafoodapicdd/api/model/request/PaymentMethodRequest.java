package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;

import javax.validation.constraints.NotBlank;

// Carga intrínsica = 1; Limite = 9
public class PaymentMethodRequest {

    // Carga: +1 (PaymentMethod)
    @UniqueValue(domainClass = PaymentMethod.class, fieldName = "description")
    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

}
