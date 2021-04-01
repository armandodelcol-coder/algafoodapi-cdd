package com.armando.algafoodapicdd.api.model.request;

import javax.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank
    private String zipcode;

    @NotBlank
    private String place;

    @NotBlank
    private String number;

    private String complement;

    @NotBlank
    private String neighborhood;

    public String getZipcode() {
        return zipcode;
    }

    public String getPlace() {
        return place;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }
}
