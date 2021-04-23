package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.response.OrderResponse;
import com.armando.algafoodapicdd.api.model.response.OrderSummaryResponse;
import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.repository.OrderRepository;
import com.armando.algafoodapicdd.domain.repository.filters.OrderFilter;
import com.armando.algafoodapicdd.infrastucture.specs.OrderSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class SearchOrdersController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderSummaryResponse> list(OrderFilter filter) {
        return orderRepository.findAll(OrderSpecs.usingFilter(filter)).stream().map(order -> new OrderSummaryResponse(order)).collect(Collectors.toList());
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
