package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.GroupPermissionAssociationRequest;
import com.armando.algafoodapicdd.domain.model.Group;
import com.armando.algafoodapicdd.domain.model.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínseca = 6; limite = 7
@RestController
@RequestMapping("/groups/{groupId}/permissions")
public class RegisterGroupPermissionsController {

    @PersistenceContext
    private EntityManager manager;

    @PutMapping("/associate")
    @Transactional
    // Carga: +1 (GroupPermissionAssociationRequest)
    public ResponseEntity<?> associate(
            @PathVariable Long groupId,
            @RequestBody @Valid GroupPermissionAssociationRequest request
    ) {
        // Carga: +1 (Group)
        Group group = findOrFail(groupId);
        // Carga: +1 (branch if)
        if (group.hasPermissionById(request.getPermissionId())) {
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
        if (!group.hasPermissionById(request.getPermissionId())) {
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
