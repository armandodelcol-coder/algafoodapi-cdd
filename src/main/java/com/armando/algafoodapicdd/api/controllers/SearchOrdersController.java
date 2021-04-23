package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.response.OrderResponse;
import com.armando.algafoodapicdd.api.model.response.OrderSummaryResponse;
import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.repository.OrderRepository;
import com.armando.algafoodapicdd.domain.repository.filters.OrderFilter;
import com.armando.algafoodapicdd.infrastucture.specs.OrderSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/orders")
public class SearchOrdersController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderSummaryResponse> list(OrderFilter filter, Pageable pageable) {
        return orderRepository.findAll(OrderSpecs.usingFilter(filter), pageable)
                .map(order -> new OrderSummaryResponse(order));
    }

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findByCode(@PathVariable String code) {
         Order order = findOrderOrFail(code);
         return new OrderResponse(order);
    }

    private Order findOrderOrFail(String code) {
        // +1
        Order order = orderRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Esse pedido não existe.")
        );
        return order;
    }

}
