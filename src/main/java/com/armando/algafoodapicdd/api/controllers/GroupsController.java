package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.GroupRequest;
import com.armando.algafoodapicdd.api.model.response.GroupResponse;
import com.armando.algafoodapicdd.domain.model.Group;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 4; Limite = 7
@RestController
@RequestMapping("/groups")
public class GroupsController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (GroupResponse); +1 (GroupRequest)
    public GroupResponse insert(@RequestBody @Valid GroupRequest groupRequest) {
        // Carga: +1 (Group)
        Group group = new Group(groupRequest.getName());
        manager.persist(group);
        return new GroupResponse(group);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GroupResponse> list() {
        return manager.createQuery("SELECT g FROM Group g", Group.class).getResultList()
                // Carga: +1 (função como argumento)
                .stream().map(group -> new GroupResponse(group))
                .collect(Collectors.toList());
    }

}
