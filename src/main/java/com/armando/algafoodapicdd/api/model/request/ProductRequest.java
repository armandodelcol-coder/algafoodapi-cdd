package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.domain.model.Product;
import com.armando.algafoodapicdd.domain.model.Restaurant;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

// Carga: 2; limite: 9
public class ProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    private Boolean active = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getActive() {
        return active;
    }

    // +1
    public Product toModel(Restaurant restaurant) {
        // +1
        Assert.state(restaurant != null, "O restaurante precisa existir para ser criado um produto nele.");

        return new Product(name, description, price, active, restaurant);
    }

}
