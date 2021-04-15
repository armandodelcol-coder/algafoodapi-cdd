package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.Group;

import javax.validation.constraints.NotNull;

public class UserGroupAssociationRequest {

    @NotNull
    @ExistsId(domainClass = Group.class)
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }
    
}
