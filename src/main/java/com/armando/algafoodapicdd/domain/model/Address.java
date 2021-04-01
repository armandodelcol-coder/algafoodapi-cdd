package com.armando.algafoodapicdd.domain.model;

import javax.persistence.*;

@Embeddable
public class Address {

    @Column(name = "address_zipcode")
    private String zipcode;

    @Column(name = "address_place")
    private String place;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @Deprecated
    public Address() {
    }

    public Address(String zipcode, String place, String number, String complement, String neighborhood) {
        this.zipcode = zipcode;
        this.place = place;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
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

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

}