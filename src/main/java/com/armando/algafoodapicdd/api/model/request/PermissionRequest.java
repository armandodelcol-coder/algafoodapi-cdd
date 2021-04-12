package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Carga intrínsica = 1; Limite = 9
public class PermissionRequest {

    @NotBlank
    // Carga: +1 (Permission)
    @UniqueValue(domainClass = Permission.class, fieldName = "name")
    @Size(max = 60)
    private String name;

    @NotBlank
    @Size(max = 300)
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Permission toModel() {
        return new Permission(name, description);
    }

}
