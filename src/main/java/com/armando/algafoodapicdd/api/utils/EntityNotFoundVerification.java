package com.armando.algafoodapicdd.api.utils;

import org.springframework.lang.Nullable;

import javax.persistence.EntityNotFoundException;

public abstract class EntityNotFoundVerification {

    public static void dispatchIfEntityIsNull(Object entity, @Nullable String message) {
        if (entity == null) throw new EntityNotFoundException(message);
    }

}
