package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.request.UserGroupAssociationRequest;
import com.armando.algafoodapicdd.domain.model.Group;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.OffsetDateTime;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupsController {

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
        if (user.hasThisGroup(request.getGroupId())) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (CustomErrorResponseBody);
                    new CustomErrorResponseBody(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Esse grupo já está associado nesse usuário",
                            OffsetDateTime.now()
                    )
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
        if (user.hasThisGroup(request.getGroupId())) {
            Group group = manager.find(Group.class, request.getGroupId());
            user.dissociateGroup(group);
            manager.persist(user);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body(
                new CustomErrorResponseBody(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Esse grupo não está associado nesse usuário",
                        OffsetDateTime.now()
                )
        );
    }

    private User findUserOrFail(Long id) {
        User user = manager.find(User.class, id);
        // Carga: +1 (EntityNotFoundVerificationHelper);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(user, "Usuário não encontrado");
        return user;
    }

}
