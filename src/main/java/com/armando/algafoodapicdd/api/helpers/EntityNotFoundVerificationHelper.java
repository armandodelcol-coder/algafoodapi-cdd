package com.armando.algafoodapicdd.api.helpers;

import org.springframework.lang.Nullable;

import javax.persistence.EntityNotFoundException;

public abstract class EntityNotFoundVerificationHelper {

    public static void dispatchIfEntityIsNull(Object entity, @Nullable String message) {
        if (entity == null) throw new EntityNotFoundException(message);
    }

}
