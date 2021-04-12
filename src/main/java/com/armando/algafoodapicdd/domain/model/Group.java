package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;

// Carga intrínsica = 0; Limite = 9
@Entity
@Table(name = "tb_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 60)
    private String name;

    @Deprecated
    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
