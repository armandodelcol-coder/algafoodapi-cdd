package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.City;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Carga intrínsica = 1; Limite = 9
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

    @NotNull
    // Carga: +1 (City)
    @ExistsId(domainClass = City.class)
    private Long cityId;

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

    public Long getCityId() {
        return cityId;
    }

}
