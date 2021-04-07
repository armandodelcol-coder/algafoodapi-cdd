package com.armando.algafoodapicdd.api.controllers;

import com.armando.algafoodapicdd.api.model.request.StateRequest;
import com.armando.algafoodapicdd.api.model.response.StateResponse;
import com.armando.algafoodapicdd.domain.model.State;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// Carga intrínsica = 4; Limite = 7
@RestController
@RequestMapping("/states")
public class CrudStatesController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    // Carga: +1 (StateResponse) +1 (StateRequest)
    public StateResponse insert(@RequestBody @Valid StateRequest stateRequest) {
        // Carga: +1 (State)
        State state = new State(stateRequest.getName());
        manager.persist(state);
        return new StateResponse(state);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StateResponse> list() {
        return manager.createQuery("SELECT state from State state", State.class)
                .getResultList()
                // Carga: +1 (função como argumento no map)
                .stream().map(state -> new StateResponse(state))
                .collect(Collectors.toList());
    }

}
