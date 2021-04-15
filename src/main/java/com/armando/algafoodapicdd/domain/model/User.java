package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.api.model.request.UserRequest;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @Deprecated
    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    // Carga: +1 (UserRequest)
    public void setPropertiesToUpdate(UserRequest userRequest) {
        this.name = userRequest.getName();
        this.email = userRequest.getEmail();
    }

    public boolean passwordEqualsTo(String currentPassword) {
        return this.password.equals(currentPassword);
    }

}
