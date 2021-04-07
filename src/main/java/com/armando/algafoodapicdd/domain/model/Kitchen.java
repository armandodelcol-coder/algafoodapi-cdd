package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_kitchen")
public class Kitchen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @OneToMany(mappedBy = "kitchen")
    // Carga: +1 (Restaurant)
    private Set<Restaurant> restaurants = new HashSet<>();

    @Deprecated
    public Kitchen() {
    }

    public Kitchen(String name) {
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

    public boolean hasAnyRestaurant() {
        return !restaurants.isEmpty();
    }
}
