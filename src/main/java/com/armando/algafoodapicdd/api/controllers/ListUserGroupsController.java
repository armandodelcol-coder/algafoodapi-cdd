package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.response.GroupResponse;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 4; Limite = 7
@RestController
@RequestMapping("/users/{userId}/groups")
public class ListUserGroupsController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Carga: +1 (GroupResponse);
    public List<GroupResponse> list(@PathVariable Long userId) {
        // Carga: +1 (User);
        User user = findUserOrFail(userId);
        return user.getGroups().stream()
                // Carga: +1 (função como argumento)
                .map(group -> new GroupResponse(group))
                .collect(Collectors.toList());
    }

    private User findUserOrFail(Long id) {
        User user = manager.find(User.class, id);
        // Carga: +1 (EntityNotFoundVerificationHelper);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(user, "Usuário não encontrado");
        return user;
    }

}
