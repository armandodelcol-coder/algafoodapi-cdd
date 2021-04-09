package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.response.CityResponse;
import com.armando.algafoodapicdd.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 5; Limite = 7
@RestController
@RequestMapping("/cities")
public class SearchCitiesController {

    // Carga: +1 (CityRepository)
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // Carga: +1 (CityResponse)
    public List<CityResponse> list() {
        return cityRepository.findAll()
                // Carga: +1 (função como argumento no map)
                .stream().map(city -> new CityResponse(city))
                .collect(Collectors.toList());
    }

    @GetMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public CityResponse findById(@PathVariable Long cityId) {
        return new CityResponse(
                // Carga: +1 (função como argumento no orElseThrow)
                cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException("Cidade não encontrada")
                )
        );
    }

}
