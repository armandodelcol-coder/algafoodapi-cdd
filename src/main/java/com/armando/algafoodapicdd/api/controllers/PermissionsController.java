package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.PermissionRequest;
import com.armando.algafoodapicdd.api.model.response.PermissionResponse;
import com.armando.algafoodapicdd.api.utils.EntityNotFoundVerification;
import com.armando.algafoodapicdd.domain.model.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 5; Limite = 7
@RestController
@RequestMapping("/permissions")
public class PermissionsController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (PermissionResponse) +1 (PermissionRequest)
    public PermissionResponse insert(@RequestBody @Valid PermissionRequest permissionRequest) {
        // Carga: +1 (Permission)
        Permission permission = permissionRequest.toModel();
        manager.persist(permission);
        return new PermissionResponse(permission);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PermissionResponse> list() {
        return manager.createQuery("SELECT p FROM Permission p", Permission.class)
                .getResultList()
                // Carga: +1 (função como argumento no map)
                .stream().map(permission -> new PermissionResponse(permission))
                .collect(Collectors.toList());
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public PermissionResponse update(
            @PathVariable Long permissionId,
            @RequestBody @Valid PermissionRequest permissionRequest
    ) {
        Permission permission = findPermissionOrFail(permissionId);
        permission.setPropertiesToUpdate(permissionRequest);
        manager.persist(permission);
        return new PermissionResponse(permission);
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable Long permissionId) {
        Permission permission = findPermissionOrFail(permissionId);
        manager.remove(permission);
    }

    private Permission findPermissionOrFail(Long permissionId) {
        Permission permission = manager.find(Permission.class, permissionId);
        // Carga: +1 (EntityNotFoundVerification);
        EntityNotFoundVerification.dispatchIfEntityIsNull(permission, "Não existe uma permissão com esse id.");
        return permission;
    }

}
