package com.armando.algafoodapicdd.api.core.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// Carga intrínsica = 1; Limite = 7
public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {

    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        this.aClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // Carga: +1 (branch if)
        if (value == null) return true;
        Object possibleObject = manager.find(aClass, value);
        return possibleObject != null;
    }

}