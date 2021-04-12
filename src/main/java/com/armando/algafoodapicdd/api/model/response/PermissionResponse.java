package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Permission;

// Carga intrínsica = 1; Limite = 7
public class PermissionResponse {

    private Long id;
    private String name;
    private String description;

    // Carga: +1 (Permission)
    public PermissionResponse(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.description = permission.getDescription();
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

}
