package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Address;

public class AddressResponse {

    private String zipcode;
    private String place;
    private String number;
    private String neighborhood;
    private String complement;

    public AddressResponse(Address address) {
        this.zipcode = address.getZipcode();
        this.place = address.getPlace();
        this.number = address.getNumber();
        this.neighborhood = address.getNeighborhood();
        this.complement = address.getComplement();
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPlace() {
        return place;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getComplement() {
        return complement;
    }
}
