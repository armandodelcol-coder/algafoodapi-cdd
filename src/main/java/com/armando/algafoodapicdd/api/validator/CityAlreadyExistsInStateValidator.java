package com.armando.algafoodapicdd.api.validator;

import com.armando.algafoodapicdd.api.model.request.CityRequest;
import com.armando.algafoodapicdd.domain.model.City;
import com.armando.algafoodapicdd.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// Carga intrínsica = 4; Limite = 7
@Component
public class CityAlreadyExistsInStateValidator implements Validator {

    @Autowired
    // Carga: +1 (CityRepository)
    private CityRepository cityRepository;

    @Override
    // Carga: +1 (CityRequest)
    public boolean supports(Class<?> aClass) {
        return CityRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        CityRequest cityRequest = (CityRequest) target;
        // Carga: +1 (City)
        City city = cityRepository.getByNameAndStateId(cityRequest.getName(), cityRequest.getStateId());
        // Carga: +1 (branch if)
        if (city == null) return;

        errors.rejectValue("stateId", null, "Já existe uma cidade com o mesmo nome cadastrada com esse estado.");
    }

}
