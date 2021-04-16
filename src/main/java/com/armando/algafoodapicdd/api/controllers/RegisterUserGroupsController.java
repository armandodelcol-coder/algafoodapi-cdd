package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.UserGroupAssociationRequest;
import com.armando.algafoodapicdd.domain.model.Group;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping("/users/{userId}/groups")
public class RegisterUserGroupsController {

    @PersistenceContext
    private EntityManager manager;

    @PutMapping("/associate")
    @Transactional
    // Carga: +1 (UserGroupAssociationRequest);
    public ResponseEntity<?> associate(
            @PathVariable Long userId,
            @RequestBody @Valid UserGroupAssociationRequest request
    ) {
        // Carga: +1 (User)
        User user = findUserOrFail(userId);
        // Carga: +1 (branch if);
        if (user.hasThisGroupById(request.getGroupId())) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (ErrorResponseBodyHelper);
                    ErrorResponseBodyHelper.badRequest("Esse grupo já está associado nesse usuário")
            );
        }

        // Carga: +1 (Group);
        Group group = manager.find(Group.class, request.getGroupId());
        user.associateGroup(group);
        manager.persist(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/dissociate")
    @Transactional
    public ResponseEntity<?> dissociate(
            @PathVariable Long userId,
            @RequestBody @Valid UserGroupAssociationRequest request
    ) {
        User user = findUserOrFail(userId);
        // Carga: +1 (branch if);
        if (user.hasThisGroupById(request.getGroupId())) {
            Group group = manager.find(Group.class, request.getGroupId());
            user.dissociateGroup(group);
            manager.persist(user);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body(
                ErrorResponseBodyHelper.badRequest("Esse grupo não está associado nesse usuário")
        );
    }

    private User findUserOrFail(Long id) {
        User user = manager.find(User.class, id);
        // Carga: +1 (EntityNotFoundVerificationHelper);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(user, "Usuário não encontrado");
        return user;
    }

}
