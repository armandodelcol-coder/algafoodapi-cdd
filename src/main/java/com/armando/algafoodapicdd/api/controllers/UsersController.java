package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.model.request.UserRequest;
import com.armando.algafoodapicdd.api.model.request.UserWithPasswordRequest;
import com.armando.algafoodapicdd.api.model.response.UserResponse;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 6; Limite = 7
@RestController
@RequestMapping("/users")
public class UsersController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (UserResponse); +1 (UserWithPasswordRequest)
    public UserResponse insert(@RequestBody @Valid UserWithPasswordRequest userRequest) {
        // Carga: +1 (User);
        User user = userRequest.toModel();
        manager.persist(user);
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    // Carga: +1 (UserRequest);
    public UserResponse update(
            @PathVariable Long userId,
            @RequestBody @Valid UserRequest userRequest
    ) {
        User user = findUserOrFail(userId);
        user.setPropertiesToUpdate(userRequest);
        manager.persist(user);
        return new UserResponse(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> list() {
        return manager.createQuery("SELECT u FROM User u", User.class).getResultList()
                // Carga: +1 (função como argumento);
                .stream().map(user -> new UserResponse(user))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findById(@PathVariable Long userId) {
        User user = findUserOrFail(userId);
        return new UserResponse(user);
    }

    private User findUserOrFail(Long id) {
        User user = manager.find(User.class, id);
        // Carga: +1 (EntityNotFoundVerificationHelper);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(user, "Usuário não encontrado");
        return user;
    }

}
