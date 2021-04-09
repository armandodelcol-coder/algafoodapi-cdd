package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.model.request.CityRequest;
import com.armando.algafoodapicdd.api.model.response.CityResponse;
import com.armando.algafoodapicdd.api.utils.EntityNotFoundVerification;
import com.armando.algafoodapicdd.api.validator.CityAlreadyExistsInStateValidator;
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
import java.time.OffsetDateTime;

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
        EntityNotFoundVerification.dispatchIfEntityIsNull(city, "Cidade não encontrada.");
        city.setPropertiesToUpdate(cityRequest, manager);
        manager.persist(city);
        return new CityResponse(city);
    }

    @DeleteMapping("/{cityId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long cityId) {
        City city = manager.find(City.class, cityId);
        EntityNotFoundVerification.dispatchIfEntityIsNull(city, "Cidade não encontrada.");
        // Carga: +1 (branch if)
        if (city.hasAnyRestaurantWithThisCityRegistered(manager)) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (CustomErrorResponseBody)
                    new CustomErrorResponseBody(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Existem endereços cadastrados com essa cidade.",
                            OffsetDateTime.now()
                    )
            );
        }

        manager.remove(city);
        return ResponseEntity.noContent().build();
    }

}
