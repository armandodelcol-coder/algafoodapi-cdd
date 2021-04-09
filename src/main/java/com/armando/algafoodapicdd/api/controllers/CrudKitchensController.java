package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.model.request.KitchenRequest;
import com.armando.algafoodapicdd.api.model.response.KitchenResponse;
import com.armando.algafoodapicdd.api.utils.EntityNotFoundVerification;
import com.armando.algafoodapicdd.domain.model.Kitchen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping(value = "/kitchens")
public class CrudKitchensController {

    @PersistenceContext
    private EntityManager manager;

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
    public List<KitchenResponse> list() {
        return manager.createQuery("SELECT kitchen FROM Kitchen kitchen", Kitchen.class)
                .getResultList().stream()
                // Carga: +1 (função como argumento no map)
                .map(kitchen -> new KitchenResponse(kitchen))
                .collect(Collectors.toList());
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
                    // Carga: +1 (CustomErrorResponseBody)
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

    private Kitchen findKitchenOrFail(Long kitchenId) {
        Kitchen kitchen = manager.find(Kitchen.class, kitchenId);
        // Carga: +1 (EntityNotFoundVerification)
        EntityNotFoundVerification.dispatchIfEntityIsNull(kitchen, "Cozinha não encontrada.");
        return kitchen;
    }

}
