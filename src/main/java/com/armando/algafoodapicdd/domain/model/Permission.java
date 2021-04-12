package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.api.model.request.PermissionRequest;

import javax.persistence.*;

// Carga intrínsica = 1; Limite = 9
@Entity
@Table(name = "tb_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 60)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT", precision = 300)
    private String description;

    @Deprecated
    public Permission() {
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Carga: +1 (PermissionRequest)
    public void setPropertiesToUpdate(PermissionRequest permissionRequest) {
        this.name = permissionRequest.getName();
        this.description = permissionRequest.getDescription();
    }

}
