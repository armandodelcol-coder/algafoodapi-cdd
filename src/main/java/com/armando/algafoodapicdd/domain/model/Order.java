package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.domain.model.enums.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Carga = 9; limite = 9
@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal deliveryTax;

    @Column(nullable = false)
    private BigDecimal total;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    private OffsetDateTime confirmedAt;

    private OffsetDateTime deliveryAt;

    private OffsetDateTime canceledAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CRIADO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @Deprecated
    public Order() {
    }

    public Order(PaymentMethod paymentMethod, Restaurant restaurant, User client, Address address, List<OrderItem> items) {
        this.paymentMethod = paymentMethod;
        this.restaurant = restaurant;
        this.client = client;
        this.address = address;
        this.items = items;
        this.deliveryTax = this.restaurant.getDeliveryTax();
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDeliveryTax() {
        return deliveryTax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public OffsetDateTime getDeliveryAt() {
        return deliveryAt;
    }

    public OffsetDateTime getCanceledAt() {
        return canceledAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getClient() {
        return client;
    }

    public Address getAddress() {
        return address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addOrderInItems() {
        getItems().forEach(item -> item.setOrder(this));
    }

    public void calcTotal() {
        this.items.forEach(OrderItem::calcTotal);
        this.subtotal = this.items.stream()
                .map(item -> item.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal.add(this.deliveryTax);
    }

    @PrePersist
    private void prePersist() {
        generateCode();
    }

    private void generateCode() {
        this.code = UUID.randomUUID().toString();
    }

}
