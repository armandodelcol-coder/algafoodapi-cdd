package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.User;

import javax.validation.constraints.NotNull;

public class RestaurantResponsibleRequest {

    @NotNull
    @ExistsId(domainClass = User.class)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

}
