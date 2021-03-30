package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.KitchenRequest;
import com.armando.algafoodapicdd.api.model.response.KitchenResponse;
import com.armando.algafoodapicdd.domain.model.Kitchen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kitchens")
public class KitchensController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public KitchenResponse insert(@RequestBody @Valid KitchenRequest kitchenRequest) {
        Kitchen kitchen = new Kitchen(kitchenRequest.getName());
        manager.persist(kitchen);
        return new KitchenResponse(kitchen);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<KitchenResponse> list() {
        return manager.createQuery("SELECT kitchen FROM Kitchen kitchen", Kitchen.class)
                .getResultList().stream()
                .map(kitchen -> new KitchenResponse(kitchen))
                .collect(Collectors.toList());
    }

    @GetMapping("/{kitchenId}")
    public ResponseEntity<KitchenResponse> findById(@PathVariable Long kitchenId) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        if (kitchen == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new KitchenResponse(kitchen));
    }

    @PutMapping("/{kitchenId}")
    @Transactional
    public ResponseEntity<KitchenResponse> update(
            @RequestBody @Valid KitchenRequest kitchenRequest,
            @PathVariable Long kitchenId
    ) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        if (kitchen == null) return ResponseEntity.notFound().build();
        kitchen.setName(kitchenRequest.getName());
        manager.persist(kitchen);
        return ResponseEntity.ok(new KitchenResponse(kitchen));
    }

    @DeleteMapping("/{kitchenId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long kitchenId) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        if (kitchen == null) return ResponseEntity.notFound().build();
        manager.remove(kitchen);
        return ResponseEntity.noContent().build();
    }

}
