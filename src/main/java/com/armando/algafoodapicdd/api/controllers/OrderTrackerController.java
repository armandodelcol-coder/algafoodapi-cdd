package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.helpers.ErrorResponseBodyHelper;
import com.armando.algafoodapicdd.domain.model.Order;
import com.armando.algafoodapicdd.domain.model.enums.OrderStatus;
import com.armando.algafoodapicdd.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

// Carga = 8; limite = 7
@RestController
@RequestMapping("/orders/{code}")
public class OrderTrackerController {

    // +1
    @Autowired
    private OrderRepository orderRepository;

    @PutMapping("/confirmation")
    public ResponseEntity<?> confirm(@PathVariable String code) {
        // +1
        Order order = findOrderOrFail(code);
        // +1
        OrderStatus confirmedStatus = OrderStatus.CONFIRMADO;
        // +1
        if (!order.getStatus().canTrackerFor(confirmedStatus)) {
            // +1
            return ResponseEntity.badRequest().body(ErrorResponseBodyHelper.badRequest(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            code,
                            order.getStatus().getDescription(),
                            confirmedStatus.getDescription()
                    )
            ));
        }

        order.confirm();
        orderRepository.save(order);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/delivery")
    public ResponseEntity<?> delivery(@PathVariable String code) {
        Order order = findOrderOrFail(code);
        OrderStatus deliveryStatus = OrderStatus.ENTREGUE;
        // +1
        if (!order.getStatus().canTrackerFor(deliveryStatus)) {
            return ResponseEntity.badRequest().body(ErrorResponseBodyHelper.badRequest(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            code,
                            order.getStatus().getDescription(),
                            deliveryStatus.getDescription()
                    )
            ));
        }

        order.delivery();
        orderRepository.save(order);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancellation")
    public ResponseEntity<?> cancellation(@PathVariable String code) {
        Order order = findOrderOrFail(code);
        OrderStatus canceledStatus = OrderStatus.CANCELADO;
        // +1
        if (!order.getStatus().canTrackerFor(canceledStatus)) {
            return ResponseEntity.badRequest().body(ErrorResponseBodyHelper.badRequest(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            code,
                            order.getStatus().getDescription(),
                            canceledStatus.getDescription()
                    )
            ));
        }

        order.cancel();
        orderRepository.save(order);
        return ResponseEntity.noContent().build();
    }

    private Order findOrderOrFail(String code) {
        // +1
        Order order = orderRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Esse pedido não existe.")
        );
        return order;
    }

}
