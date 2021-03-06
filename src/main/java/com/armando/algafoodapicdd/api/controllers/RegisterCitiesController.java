package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.api.model.request.CityRequest;
import com.armando.algafoodapicdd.api.model.response.CityResponse;
import com.armando.algafoodapicdd.api.helpers.EntityNotFoundVerificationHelper;
import com.armando.algafoodapicdd.api.core.validator.CityAlreadyExistsInStateValidator;
import com.armando.algafoodapicdd.domain.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping("/cities")
public class RegisterCitiesController {

    @Autowired
    // Carga: +1 (CityAlreadyExistsInStateValidator)
    private CityAlreadyExistsInStateValidator cityAlreadyExistsInStateValidator;

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(cityAlreadyExistsInStateValidator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (CityResponse) +1 (CityRequest)
    public CityResponse insert(@RequestBody @Valid CityRequest cityRequest) {
        // Carga: +1 (City)
        City city = cityRequest.toModel(manager);
        manager.persist(city);
        return new CityResponse(city);
    }

    @PutMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public CityResponse update(
            @PathVariable Long cityId,
            @RequestBody @Valid CityRequest cityRequest
    ) {
        City city = manager.find(City.class, cityId);
        // Carga: +1 (EntityVerification)
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(city, "Cidade não encontrada.");
        city.setPropertiesToUpdate(cityRequest, manager);
        manager.persist(city);
        return new CityResponse(city);
    }

    @DeleteMapping("/{cityId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long cityId) {
        City city = manager.find(City.class, cityId);
        EntityNotFoundVerificationHelper.dispatchIfEntityIsNull(city, "Cidade não encontrada.");
        // Carga: +1 (branch if)
        if (city.hasAnyRestaurantWithThisCityRegistered(manager)) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (ErrorResponseBodyHelper)
                    ErrorResponseBodyHelper.badRequest("Existem endereços cadastrados com essa cidade.")
            );
        }

        manager.remove(city);
        return ResponseEntity.noContent().build();
    }

}
