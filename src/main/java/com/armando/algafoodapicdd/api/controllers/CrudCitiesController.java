package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.CityRequest;
import com.armando.algafoodapicdd.api.model.response.CityResponse;
import com.armando.algafoodapicdd.api.validator.CityAlreadyExistsInStateValidator;
import com.armando.algafoodapicdd.domain.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CrudCitiesController {

    @Autowired
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
    public CityResponse insert(@RequestBody @Valid CityRequest cityRequest) {
        City city = cityRequest.toModel(manager);
        manager.persist(city);
        return new CityResponse(city);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityResponse> list() {
        return manager.createQuery("SELECT city from City city", City.class).getResultList()
                .stream().map(city -> new CityResponse(city))
                .collect(Collectors.toList());
    }

}
