package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.KitchenRequest;
import com.armando.algafoodapicdd.api.model.response.KitchenResponse;
import com.armando.algafoodapicdd.domain.model.Kitchen;
import com.armando.algafoodapicdd.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínsica = 8; Limite = 7
@RestController
@RequestMapping(value = "/kitchens")
public class KitchensController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    // +1
    private KitchenRepository kitchenRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (KitchenResponse) +1 (KitchenRequest)
    public KitchenResponse insert(@RequestBody @Valid KitchenRequest kitchenRequest) {
        // Carga: +1 (Kitchen)
        Kitchen kitchen = new Kitchen(kitchenRequest.getName());
        manager.persist(kitchen);
        return new KitchenResponse(kitchen);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<KitchenResponse> list(Pageable pageable) {
        // +1
        return kitchenRepository.findAll(pageable).map(kitchen -> new KitchenResponse(kitchen));
    }

    @GetMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenResponse findById(@PathVariable Long kitchenId) {
        Kitchen kitchen = findKitchenOrFail(kitchenId);
        return new KitchenResponse(kitchen);
    }

    @PutMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public KitchenResponse update(
            @RequestBody @Valid KitchenRequest kitchenRequest,
            @PathVariable Long kitchenId
    ) {
        Kitchen kitchen = findKitchenOrFail(kitchenId);
        kitchen.setName(kitchenRequest.getName());
        manager.persist(kitchen);
        return new KitchenResponse(kitchen);
    }

    @DeleteMapping("/{kitchenId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long kitchenId) {
        Kitchen kitchen = findKitchenOrFail(kitchenId);
        // Carga: +1 (branch if)
        if (kitchen.hasAnyRestaurant()) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (ErrorResponseBodyHelper)
                    ErrorResponseBodyHelper.badRequest("Existem Restaurantes relacionados a essa Cozinha.")
            );
        }
        manager.remove(kitchen);
        return ResponseEntity.noContent().build();
    }

    private Kitchen findKitchenOrFail(Long kitchenId) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(kitchen, "Cozinha não encontrada.");
        return kitchen;
    }

}
