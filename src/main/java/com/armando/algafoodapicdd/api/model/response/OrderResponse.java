package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    private String code;
    private BigDecimal subTotal;
    private BigDecimal deliveryTax;
    private BigDecimal total;
    private OffsetDateTime createdAt;
    private OffsetDateTime confirmedAt;
    private OffsetDateTime deliveryAt;
    private OffsetDateTime canceledAt;
    private OrderStatus status;
    private RestaurantSummaryResponse restaurant;
    private PaymentMethodResponse paymentMethod;
    private UserResponse client;
    private AddressResponse address;
    private List<OrderItemResponse> items;

    public OrderResponse(Order order) {
        this.code = order.getCode();
        this.subTotal = order.getSubtotal();
        this.deliveryTax = order.getDeliveryTax();
        this.total = order.getTotal();
        this.createdAt = order.getCreatedAt();
        this.confirmedAt = order.getConfirmedAt();
        this.deliveryAt = order.getDeliveryAt();
        this.canceledAt = order.getCanceledAt();
        this.status = order.getStatus();
        this.restaurant = new RestaurantSummaryResponse(order.getRestaurant());
        this.paymentMethod = new PaymentMethodResponse(order.getPaymentMethod());
        this.client = new UserResponse(order.getClient());
        this.address = new AddressResponse(order.getAddress());
        this.items = order.getItems().stream().map(orderItem -> new OrderItemResponse(orderItem)).collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
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

    public RestaurantSummaryResponse getRestaurant() {
        return restaurant;
    }

    public PaymentMethodResponse getPaymentMethod() {
        return paymentMethod;
    }

    public UserResponse getClient() {
        return client;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

}
