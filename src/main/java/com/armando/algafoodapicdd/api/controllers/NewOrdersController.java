package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.OrderRequest;
import com.armando.algafoodapicdd.api.model.response.OrderResponse;
import com.armando.algafoodapicdd.api.validator.CityOfRestaurantValidator;
import com.armando.algafoodapicdd.api.validator.PaymentMethodBelongsToRestaurantValidator;
import com.armando.algafoodapicdd.api.validator.ProductsBelongsToRestaurantValidator;
import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Carga: 6; limite = 7
@RestController
@RequestMapping("/orders")
public class NewOrdersController {

    // +1
    @Autowired
    PaymentMethodBelongsToRestaurantValidator paymentMethodBelongsToRestaurantValidator;

    // +1
    @Autowired
    CityOfRestaurantValidator cityOfRestaurantValidator;

    // +1
    @Autowired
    ProductsBelongsToRestaurantValidator productsBelongsToRestaurantValidator;

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.addValidators(paymentMethodBelongsToRestaurantValidator);
        binder.addValidators(cityOfRestaurantValidator);
        binder.addValidators(productsBelongsToRestaurantValidator);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // +1
    // +1
    public OrderResponse insert(@RequestBody @Valid OrderRequest orderRequest) {
        // +1
        Order order = orderRequest.toModel(manager, manager.find(User.class, 1L));
        manager.persist(order);
        return new OrderResponse(order);
    }


}
