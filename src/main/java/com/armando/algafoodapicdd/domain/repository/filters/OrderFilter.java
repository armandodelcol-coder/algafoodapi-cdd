package com.armando.algafoodapicdd.domain.repository.filters;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class OrderFilter {

    private Long clientId;
    private Long restaurantId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAtBegin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAtEnd;

    public Long getClientId() {
        return clientId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public OffsetDateTime getCreatedAtBegin() {
        return createdAtBegin;
    }

    public OffsetDateTime getCreatedAtEnd() {
        return createdAtEnd;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setCreatedAtBegin(OffsetDateTime createdAtBegin) {
        this.createdAtBegin = createdAtBegin;
    }

    public void setCreatedAtEnd(OffsetDateTime createdAtEnd) {
        this.createdAtEnd = createdAtEnd;
    }

}