package com.armando.algafoodapicdd.domain.model;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

// Carga intrínsica = 0; Limite = 9
@Entity
@Table(name = "tb_paymentmethod")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

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

}
