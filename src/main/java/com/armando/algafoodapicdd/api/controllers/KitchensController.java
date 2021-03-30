package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.KitchenRequest;
import com.armando.algafoodapicdd.domain.model.Kitchen;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/kitchens")
public class KitchensController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public String insert(@RequestBody @Valid KitchenRequest kitchenRequest) {
        Kitchen kitchen = new Kitchen(kitchenRequest.getName());
        manager.persist(kitchen);
        return kitchen.toString();
    }

}
