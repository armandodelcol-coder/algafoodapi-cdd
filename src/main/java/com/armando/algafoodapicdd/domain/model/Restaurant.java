package com.armando.algafoodapicdd.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

// Carga intrínsica = 2; Limite = 9
@Entity
@Table(name = "tb_restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal deliveryTax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
    // Carga: +1 (Kitchen)
    private Kitchen kitchen;

    private Boolean active = Boolean.FALSE;

    private Boolean open = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

    @Embedded
    // Carga: +1 (Address)
    private Address address;

    @Deprecated
    public Restaurant() {
    }

    public Restaurant(String name, BigDecimal deliveryTax, Kitchen kitchen, Address address) {
        this.name = name;
        this.deliveryTax = deliveryTax;
        this.kitchen = kitchen;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryTax() {
        return deliveryTax;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getOpen() {
        return open;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Address getAddress() {
        return address;
    }
}
