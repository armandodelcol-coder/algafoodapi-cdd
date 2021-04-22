package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.OrderItem;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
    private String observation;
    private Long productId;
    private String productName;

    public OrderItemResponse(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.total = orderItem.getTotal();
        this.observation = orderItem.getObservation();
        this.productId = orderItem.getProduct().getId();
        this.productName = orderItem.getProduct().getName();
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

    public BigDecimal getTotal() {
        return total;
    }

    public String getObservation() {
        return observation;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

}
