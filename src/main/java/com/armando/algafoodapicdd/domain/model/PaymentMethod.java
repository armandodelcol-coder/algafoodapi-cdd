package com.armando.algafoodapicdd.domain.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "tb_restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    // Carga: +1 (PaymentMethod)
    private List<Restaurant> restaurants = new ArrayList<>();

    @Deprecated
    public PaymentMethod() {
    }

    public PaymentMethod(@NotBlank String description) {
        Assert.state(!description.isBlank(), "Não é permitido setar uma descrição de forma de pagamento nula ou em branco.");

        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        Assert.state(!description.isBlank(), "Não é permitido setar uma descrição de forma de pagamento nula ou em branco.");

        this.description = description;
    }

    public boolean isAssociateWithAnyRestaurant() {
        return !restaurants.isEmpty();
    }

}
