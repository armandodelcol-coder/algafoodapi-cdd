package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "state")
    // Carga: +1 (City)
    private Set<City> cities = new HashSet<>();

    @Deprecated
    public State() {
    }

    public State(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasAnyCity() {
        return !cities.isEmpty();
    }

}
