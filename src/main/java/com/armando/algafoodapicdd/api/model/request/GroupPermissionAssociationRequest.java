package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.Permission;

import javax.validation.constraints.NotNull;

// Carga intrínseca: 1; limite: 9
public class GroupPermissionAssociationRequest {

    @NotNull
    // Carga: +1 (Permission)
    @ExistsId(domainClass = Permission.class)
    private Long permissionId;

    public Long getPermissionId() {
        return permissionId;
    }

}
