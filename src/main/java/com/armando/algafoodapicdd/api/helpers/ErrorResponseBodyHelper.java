package com.armando.algafoodapicdd.api.helpers;

import com.armando.algafoodapicdd.api.exceptionhandler.CustomErrorResponseBody;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

// Carga intrínsica = 1; Limite = 7
public abstract class ErrorResponseBodyHelper {

    public static CustomErrorResponseBody badRequest(String message) {
        // Carga: +1 (CustomErrorResponseBody)
        return new CustomErrorResponseBody(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                OffsetDateTime.now()
        );
    }

}
