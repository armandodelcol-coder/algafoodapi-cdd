package com.armando.algafoodapicdd.domain.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "paymentMethods")
    // Carga: +1 (Restaurant)
    private Set<Restaurant> restaurants = new HashSet<>();

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
