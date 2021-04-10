package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.PaymentMethodRequest;
import com.armando.algafoodapicdd.api.model.response.PaymentMethodResponse;
import com.armando.algafoodapicdd.api.utils.EntityNotFoundVerification;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 5; Limite = 7
@RestController
@RequestMapping("/paymentmethods")
public class CrudPaymentMethodsController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (PaymentMethodResponse); +1 (PaymentMethodRequest)
    public PaymentMethodResponse insert(@RequestBody @Valid PaymentMethodRequest paymentMethodRequest) {
        // Carga: +1 (PaymentMethod);
        PaymentMethod paymentMethod = new PaymentMethod(paymentMethodRequest.getDescription());
        manager.persist(paymentMethod);
        return new PaymentMethodResponse(paymentMethod);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodResponse> list() {
        return manager.createQuery("SELECT pm FROM PaymentMethod pm", PaymentMethod.class).getResultList()
                // Carga: +1 (função como argumento no map);
                .stream().map(paymentMethod -> new PaymentMethodResponse(paymentMethod))
                .collect(Collectors.toList());
    }

    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public PaymentMethodResponse update(
            @PathVariable Long paymentMethodId,
            @RequestBody @Valid PaymentMethodRequest paymentMethodRequest
    ) {
        PaymentMethod paymentMethod = findPaymentMethodOrFail(paymentMethodId);
        paymentMethod.setDescription(paymentMethodRequest.getDescription());
        manager.persist(paymentMethod);
        return new PaymentMethodResponse(paymentMethod);
    }

    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable Long paymentMethodId) {
        PaymentMethod paymentMethod = findPaymentMethodOrFail(paymentMethodId);
        manager.remove(paymentMethod);
    }

    private PaymentMethod findPaymentMethodOrFail(Long paymentMethodId) {
        PaymentMethod paymentMethod = manager.find(PaymentMethod.class, paymentMethodId);
        // Carga: +1 (EntityNotFoundVerification);
        EntityNotFoundVerification.dispatchIfEntityIsNull(paymentMethod, "Não existe uma forma de pagamento com esse id.");
        return paymentMethod;
    }

}
