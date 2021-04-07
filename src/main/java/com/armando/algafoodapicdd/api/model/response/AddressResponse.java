package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Address;

// Carga intrínsica = 2; Limite = 7
public class AddressResponse {

    private String zipcode;
    private String place;
    private String number;
    private String neighborhood;
    private String complement;
    // Carga: +1 (CityResponse)
    private CityResponse city;

    // Carga: +1 (Address)
    public AddressResponse(Address address) {
        this.zipcode = address.getZipcode();
        this.place = address.getPlace();
        this.number = address.getNumber();
        this.neighborhood = address.getNeighborhood();
        this.complement = address.getComplement();
        this.city = new CityResponse(address.getCity());
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

    public CityResponse getCity() {
        return city;
    }

}
