package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.ExistsId;
import com.armando.algafoodapicdd.api.core.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.Address;
import com.armando.algafoodapicdd.domain.model.City;
import com.armando.algafoodapicdd.domain.model.Kitchen;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

// Carga intrínsica = 4; Limite = 9
public class RestaurantRequest {

    @NotBlank
    // Carga: +1 (Restaurant)
    @UniqueValue(domainClass = Restaurant.class, fieldName = "name")
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal deliveryTax;

    @NotNull
    // Carga: +1 (Kitchen)
    @ExistsId(domainClass = Kitchen.class)
    private Long kitchenId;

    @NotNull
    @Valid
    // Carga: +1 (AddressRequest)
    private AddressRequest address;

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryTax() {
        return deliveryTax;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public Long getKitchenId() {
        return kitchenId;
    }

    public Restaurant toModel(EntityManager manager) {
        Kitchen kitchen = manager.find(Kitchen.class, this.kitchenId);
        Assert.state(kitchen != null, "Não é possível criar um Restaurante com uma Cozinha inexistente");

        // Carga: +1 (City)
        City city = manager.find(City.class, this.address.getCityId());
        Assert.state(city != null, "Não é possível criar um Endereço com uma Cidade inexistente");

        Address address = new Address(
                this.address.getZipcode(),
                this.address.getPlace(),
                this.address.getNumber(),
                this.address.getComplement(),
                this.address.getNeighborhood(),
                city
        );
        return new Restaurant(this.name, this.deliveryTax, kitchen, address);
    }

}
