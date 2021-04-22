package com.armando.algafoodapicdd.domain.model.enums;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO, CONFIRMADO);

    private String description;
    private List<OrderStatus> olderStatus;

    OrderStatus(String description, OrderStatus... olderStatus) {
        this.description = description;
        this.olderStatus = Arrays.asList(olderStatus);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean canTrackerFor(OrderStatus newStatus) {
        return newStatus.olderStatus.contains(this);
    }

}