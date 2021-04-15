package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Carga intrínsica = 1; Limite = 9
public class UserRequest {

    @NotBlank
    @Size(max = 60)
    private String name;

    @Email
    @NotBlank
    // Carga: +1 (User)
    @UniqueValue(domainClass = User.class, fieldName = "email")
    private String email;

    public UserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
