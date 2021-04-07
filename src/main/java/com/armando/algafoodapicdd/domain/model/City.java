package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

// Carga intrínsica = 1; Limite = 9
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

}
