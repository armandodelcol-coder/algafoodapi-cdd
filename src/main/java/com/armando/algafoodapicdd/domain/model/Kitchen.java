package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_kitchen")
public class Kitchen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    public Kitchen(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Kitchen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
