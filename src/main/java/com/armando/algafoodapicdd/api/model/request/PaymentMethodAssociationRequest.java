package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;

import javax.validation.constraints.NotNull;

public class PaymentMethodAssociationRequest {

    @NotNull
    @ExistsId(domainClass = PaymentMethod.class)
    private Long paymentMethodId;

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

}
