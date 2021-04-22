package com.armando.algafoodapicdd.api.validator;

import com.armando.algafoodapicdd.api.model.request.OrderRequest;
import com.armando.algafoodapicdd.domain.model.City;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CityOfRestaurantValidator implements Validator {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // if (errors.hasErrors()) return;

        OrderRequest request = (OrderRequest) target;
        Restaurant restaurant = manager.find(Restaurant.class, request.getRestaurantId());
        City city = manager.find(City.class, request.getDeliveryAddress().getCityId());
        if (restaurant.getAddress().getCity().equals(city)) return;

        errors.rejectValue("deliveryAddress.cityId", null, "Cidade informada não é a mesma do restaurante informado.");
    }

}
