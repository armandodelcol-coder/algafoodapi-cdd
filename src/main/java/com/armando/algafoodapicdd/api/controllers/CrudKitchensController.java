package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.model.request.KitchenRequest;
import com.armando.algafoodapicdd.api.model.response.KitchenResponse;
import com.armando.algafoodapicdd.domain.model.Kitchen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kitchens")
public class CrudKitchensController {

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
    @ResponseStatus(HttpStatus.OK)
    public KitchenResponse findById(@PathVariable Long kitchenId) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        checkKitchenExistence(kitchen);
        return new KitchenResponse(kitchen);
    }

    @PutMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public KitchenResponse update(
            @RequestBody @Valid KitchenRequest kitchenRequest,
            @PathVariable Long kitchenId
    ) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        checkKitchenExistence(kitchen);
        kitchen.setName(kitchenRequest.getName());
        manager.persist(kitchen);
        return new KitchenResponse(kitchen);
    }

    @DeleteMapping("/{kitchenId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long kitchenId) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        checkKitchenExistence(kitchen);
        if (kitchen.hasAnyRestaurant()) {
            return ResponseEntity.badRequest().body(
                    new CustomErrorResponseBody(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                            "Existem Restaurantes relacionados a essa Cozinha.",
                            OffsetDateTime.now()
                    )
            );
        }
        manager.remove(kitchen);
        return ResponseEntity.noContent().build();
    }

    private void checkKitchenExistence(Kitchen kitchen) {
        if (kitchen == null) throw new EntityNotFoundException("Cozinha não encontrada.");
    }

}
