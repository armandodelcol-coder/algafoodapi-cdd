package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import com.armando.algafoodapicdd.api.model.request.PaymentMethodRequest;
import com.armando.algafoodapicdd.api.model.response.PaymentMethodResponse;
import com.armando.algafoodapicdd.api.utils.EntityNotFoundVerification;
import com.armando.algafoodapicdd.domain.model.PaymentMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 7; Limite = 7
@RestController
@RequestMapping("/paymentmethods")
public class PaymentMethodsController {

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
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long paymentMethodId) {
        PaymentMethod paymentMethod = findPaymentMethodOrFail(paymentMethodId);
        // Carga: +1 (branch if);
        if (paymentMethod.isAssociateWithAnyRestaurant()) {
            return ResponseEntity.badRequest().body(
                    // Carga: +1 (CustomErrorResponseBody);
                    new CustomErrorResponseBody(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Existem Restaurantes associados a essa forma de pagamento",
                            OffsetDateTime.now()
                    )
            );
        }

        manager.remove(paymentMethod);
        return ResponseEntity.noContent().build();
    }

    private PaymentMethod findPaymentMethodOrFail(Long paymentMethodId) {
        PaymentMethod paymentMethod = manager.find(PaymentMethod.class, paymentMethodId);
        // Carga: +1 (EntityNotFoundVerification);
        EntityNotFoundVerification.dispatchIfEntityIsNull(paymentMethod, "Não existe uma forma de pagamento com esse id.");
        return paymentMethod;
    }

}
