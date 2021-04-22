package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.api.model.request.CityRequest;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

// Carga intrínsica = 3; Limite = 9
@Entity
@Table(name = "tb_city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    // Carga: +1 (State)
    private State state;

    @Deprecated
    public City() {
    }

    public City(@NotBlank String name, State state) {
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    // Carga: +1 (CityRequest)
    public void setPropertiesToUpdate(CityRequest cityRequest, EntityManager manager) {
        State state = manager.find(State.class, cityRequest.getStateId());
        Assert.state(state != null, "Não é permitido salvar uma Cidade com um Estado inexistente");

        this.name = cityRequest.getName();
        this.state = state;
    }

    public boolean hasAnyRestaurantWithThisCityRegistered(EntityManager manager) {
        // Carga: +1 (Restaurant)
        return !manager.createQuery("SELECT restaurant FROM Restaurant restaurant WHERE restaurant.address.city.id = :cityId", Restaurant.class)
                .setParameter("cityId", this.id)
                .getResultList()
                .isEmpty();
    }

}
