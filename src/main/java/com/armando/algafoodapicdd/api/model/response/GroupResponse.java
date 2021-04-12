package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Group;

// Carga intrínsica = 0; Limite = 7
public class GroupResponse {

    private Long id;
    private String name;

    public GroupResponse(Group group) {
        this.id = group.getId();
        this.name = group.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
