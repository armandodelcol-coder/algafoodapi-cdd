package com.armando.algafoodapicdd.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERROR_GENERIC = "Ocorreu um erro inesperado, por favor entre em contato com o administrador do sistema.";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomExceptionBody body = new CustomExceptionBody(
                status.value(),
                status.getReasonPhrase(),
                String.format(
                        "A propriedade '%s' recebeu um valor %s que é inválido para o tipo %s.",
                        ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()),
                OffsetDateTime.now()
        );
        return super.handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() instanceof PropertyBindingException || ex.getCause() instanceof IgnoredPropertyException) {
            String path = joinPath(((PropertyBindingException) ex.getCause()).getPath());
            CustomExceptionBody body = new CustomExceptionBody(
                    status.value(),
                    status.getReasonPhrase(),
                    String.format("A propriedade '%s' informada não é reconhecida no sistema.", path),
                    OffsetDateTime.now()
            );
            return super.handleExceptionInternal(ex, body, headers, status, request);
        } else if (ex.getCause() instanceof InvalidFormatException) {
            String path = joinPath(((InvalidFormatException) ex.getCause()).getPath());
            CustomExceptionBody body = new CustomExceptionBody(
                    status.value(),
                    status.getReasonPhrase(),
                    String.format(
                            "A propriedade '%s' recebeu um valor %s que é inválido para o tipo %s.",
                            path, ((InvalidFormatException) ex.getCause()).getValue(), ((InvalidFormatException) ex.getCause()).getTargetType().getSimpleName()),
                    OffsetDateTime.now()
            );
            return super.handleExceptionInternal(ex, body, headers, status, request);
        }

        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomExceptionBody body = new CustomExceptionBody(
                status.value(),
                status.getReasonPhrase(),
                "Um ou mais campos de entrada estão inválidos, verifique o preenchimento e tente novamente.",
                OffsetDateTime.now()
        );

        BindingResult bindingResult = ex.getBindingResult();
        List<CustomExceptionBody.Detail> invalidFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    // Message Source serve para viabilizar que a mensagem dos campos possam ser tratadas no arquivo messages.properties
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    return new CustomExceptionBody.Detail(fieldError.getField(), message);
                }).collect(Collectors.toList());
        body.setDetails(invalidFields);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = new CustomExceptionBody(
                    status.value(),
                    status.getReasonPhrase(),
                    MSG_ERROR_GENERIC,
                    OffsetDateTime.now()
            );
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

}
