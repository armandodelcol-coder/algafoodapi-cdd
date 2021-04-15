package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.User;

import java.time.OffsetDateTime;

// Carga intrínsica = 1; Limite = 7
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private OffsetDateTime createdAt;

    // Carga: +1 (User)
    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
