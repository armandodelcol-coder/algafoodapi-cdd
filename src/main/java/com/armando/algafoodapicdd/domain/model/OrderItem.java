package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;

// Carga = 5; limite = 9
@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal total;

    private String observation;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public BigDecimal getTotal() {
        return total;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Deprecated
    public OrderItem() {
    }

    public OrderItem(Integer quantity, BigDecimal price, String observation, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.observation = observation;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getObservation() {
        return observation;
    }

    public Product getProduct() {
        return product;
    }

    public void calcTotal() {
        if (this.price == null || this.quantity == null) {
            this.total = BigDecimal.ZERO;
        } else {
            this.total = price.multiply(new BigDecimal(quantity));
        }
    }

}
