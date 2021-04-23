package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderSummaryResponse {

    private String code;
    private BigDecimal subtotal;
    private BigDecimal deliveryTax;
    private BigDecimal total;
    private OffsetDateTime createdAt;
    private OrderStatus status = OrderStatus.CRIADO;
    private RestaurantSummaryResponse restaurant;
    private UserResponse client;

    public OrderSummaryResponse(Order order) {
        this.code = order.getCode();
        this.subtotal = order.getSubtotal();
        this.deliveryTax = order.getDeliveryTax();
        this.total = order.getTotal();
        this.createdAt = order.getCreatedAt();
        this.status = order.getStatus();
        this.restaurant = new RestaurantSummaryResponse(order.getRestaurant());
        this.client = new UserResponse(order.getClient());
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

    public OrderStatus getStatus() {
        return status;
    }

    public RestaurantSummaryResponse getRestaurant() {
        return restaurant;
    }

    public UserResponse getClient() {
        return client;
    }

}
