package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.response.PermissionResponse;
import com.armando.algafoodapicdd.domain.model.Group;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínseca = 4; limite = 7
@RestController
@RequestMapping("/groups/{groupId}/permissions")
public class ListGroupPermissionsController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Carga: +1 (PermissionResponse)
    public List<PermissionResponse> list(@PathVariable Long groupId) {
        // Carga: +1 (Group)
        Group group = findOrFail(groupId);
        return group.getPermissions()
                // Carga: +1 (função como argumento)
                .stream().map(permission -> new PermissionResponse(permission))
                .collect(Collectors.toList());
    }

    private Group findOrFail(Long id) {
        Group group = manager.find(Group.class, id);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(group, "Grupo não encontrado.");
        return group;
    }

}
