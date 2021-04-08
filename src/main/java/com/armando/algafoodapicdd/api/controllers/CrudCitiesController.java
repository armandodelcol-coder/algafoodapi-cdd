package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.model.request.CityRequest;
import com.armando.algafoodapicdd.api.model.response.CityResponse;
import com.armando.algafoodapicdd.api.validator.CityAlreadyExistsInStateValidator;
import com.armando.algafoodapicdd.domain.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 8; Limite = 7
@RestController
@RequestMapping("/cities")
public class CrudCitiesController {

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityResponse> list() {
        return manager.createQuery("SELECT city from City city join fetch city.state", City.class).getResultList()
                // Carga: +1 (função como argumento no map)
                .stream().map(city -> new CityResponse(city))
                .collect(Collectors.toList());
    }

    @GetMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public CityResponse findById(@PathVariable Long cityId) {
        City city = manager.find(City.class, cityId);
        checkCityExistence(city);
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
        checkCityExistence(city);
        city.setPropertiesToUpdate(cityRequest, manager);
        manager.persist(city);
        return new CityResponse(city);
    }

    @DeleteMapping("/{cityId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long cityId) {
        City city = manager.find(City.class, cityId);
        checkCityExistence(city);
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

    private void checkCityExistence(City city) {
        // Carga: +1 (branch if)
        if (city == null) throw new EntityNotFoundException("Cidade não encontrada.");
    }

}
