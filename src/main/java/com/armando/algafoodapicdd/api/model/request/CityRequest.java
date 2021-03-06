package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.City;
import com.armando.algafoodapicdd.domain.model.State;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Carga intrínsica = 2; Limite = 9
public class CityRequest {

    @NotBlank
    private String name;

    @NotNull
    // Carga: +1 (State)
    @ExistsId(domainClass = State.class)
    private Long stateId;

    public CityRequest(@NotBlank String name, @NotNull @ExistsId(domainClass = State.class) Long stateId) {
        this.name = name;
        this.stateId = stateId;
    }

    // Carga: +1 (City)
    public City toModel(EntityManager manager) {
        State state = manager.find(State.class, stateId);
        Assert.state(state != null, "Não é permitido criar uma Cidade com um Estado inexistente");

        return new City(
                this.name,
                state
        );
    }

    public String getName() {
        return name;
    }

    public Long getStateId() {
        return stateId;
    }
}
