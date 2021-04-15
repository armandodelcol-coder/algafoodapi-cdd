package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.domain.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Carga intrínsica = 2; Limite = 9
// Carga: +1 (UserRequest)
public class UserWithPasswordRequest extends UserRequest{

    @NotBlank
    @Size(max = 20)
    private String password;

    public UserWithPasswordRequest(String name, String email, String password) {
        super(name, email);
        this.password = password;
    }

    // Carga: +1 (User)
    public User toModel() {
        return new User(super.getName(), super.getEmail(), password);
    }

}
