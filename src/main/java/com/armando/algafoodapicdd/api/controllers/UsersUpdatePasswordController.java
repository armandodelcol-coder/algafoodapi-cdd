package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.UserUpdatePasswordRequest;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínsica = 5; Limite = 7
@RestController
@RequestMapping("/users")
public class UsersUpdatePasswordController {

    @PersistenceContext
    private EntityManager manager;

    @PutMapping("/{userId}/password")
    @Transactional
    // Carga: +1 (UserUpdatePasswordRequest);
    public ResponseEntity<?> updatePassword(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdatePasswordRequest userUpdatePasswordRequest
    ) {
        User user = findUserOrFail(userId);
        // Carga: +1 (branch if);
        if (!user.passwordEqualsTo(userUpdatePasswordRequest.getCurrentPassword())) {
            // Carga: +1 (ErrorResponseBodyHelper);
            return ResponseEntity.badRequest().body(
                    ErrorResponseBodyHelper.badRequest("Password atual está incorreto")
            );
        }

        user.setPassword(userUpdatePasswordRequest.getNewPassword());
        manager.persist(user);
        return ResponseEntity.noContent().build();
    }


    // Carga: +1 (User);
    private User findUserOrFail(Long id) {
        User user = manager.find(User.class, id);
        // Carga: +1 (EntityNotFoundVerificationHelper);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(user, "Usuário não encontrado");
        return user;
    }

}
