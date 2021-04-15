package com.armando.algafoodapicdd.api.model.request;

import javax.validation.constraints.NotBlank;

// Carga intrínsica = 0; Limite = 9
public class UserUpdatePasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
