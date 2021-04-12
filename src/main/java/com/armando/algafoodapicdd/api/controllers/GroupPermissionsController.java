package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.GroupPermissionAssociationRequest;
import com.armando.algafoodapicdd.api.model.response.PermissionResponse;
import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.utils.GroupPermissionUtil;
import com.armando.algafoodapicdd.domain.model.Group;
import com.armando.algafoodapicdd.domain.model.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínseca = 9; limite = 7
@RestController
@RequestMapping("/groups/{groupId}/permissions")
public class GroupPermissionsController {

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

    @PutMapping("/associate")
    @Transactional
    // Carga: +1 (GroupPermissionAssociationRequest)
    public ResponseEntity<?> associate(
            @PathVariable Long groupId,
            @RequestBody @Valid GroupPermissionAssociationRequest request
    ) {
        Group group = findOrFail(groupId);
        // Carga: +1 (GroupPermissionUtil); +1 (branch if)
        if (GroupPermissionUtil.exists(group, request.getPermissionId())) {
            return ResponseEntity.badRequest()
                    // Carga: +1 (ErrorResponseBodyHelper)
                    .body(ErrorResponseBodyHelper.badRequest("Já existe essa permissão associada a esse grupo."));
        }

        group.associatePermission(manager.find(Permission.class, request.getPermissionId()));
        manager.persist(group);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/dissociate")
    @Transactional
    public ResponseEntity<?> dissociate(
            @PathVariable Long groupId,
            @RequestBody @Valid GroupPermissionAssociationRequest request
    ) {
        Group group = findOrFail(groupId);
        // Carga: +1 (branch if)
        if (!GroupPermissionUtil.exists(group, request.getPermissionId())) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponseBodyHelper.badRequest("Não existe essa permissão associada a esse grupo."));
        }

        group.dissociatePermission(manager.find(Permission.class, request.getPermissionId()));
        manager.persist(group);
        return ResponseEntity.noContent().build();
    }

    private Group findOrFail(Long id) {
        Group group = manager.find(Group.class, id);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(group, "Grupo não encontrado.");
        return group;
    }

}
