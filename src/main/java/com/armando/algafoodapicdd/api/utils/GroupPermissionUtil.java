package com.armando.algafoodapicdd.api.utils;

import com.armando.algafoodapicdd.domain.model.Group;
import com.armando.algafoodapicdd.domain.model.Permission;

import java.util.Optional;

// Carga intrínsica = 3; Limite = 7
public abstract class GroupPermissionUtil {

    // Carga: +1 (Group)
    public static Boolean exists(Group group, Long permissionId) {
        // Carga: +1 (Permission)
        Optional<Permission> permissionFound = group.getPermissions().stream()
                // Carga: +1 (função como argumento no filter)
                .filter(permission -> permission.getId() == permissionId)
                .findAny();
        return permissionFound.isPresent();
    }

}
