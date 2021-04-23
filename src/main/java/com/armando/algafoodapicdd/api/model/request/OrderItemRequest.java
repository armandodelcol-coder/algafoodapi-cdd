package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.OrderItem;
import com.armando.algafoodapicdd.domain.model.Product;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Component
public class OrderItemRequest {

    @NotNull
    @ExistsId(domainClass = Product.class)
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

    @Length(max = 255)
    private String observation;

    @Deprecated
    public OrderItemRequest() {
    }

    public OrderItemRequest(Long productId, Integer quantity, String observation) {
        this.productId = productId;
        this.quantity = quantity;
        this.observation = observation;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getObservation() {
        return observation;
    }

    public OrderItem toModel(EntityManager manager) {
        Product product = manager.find(Product.class, productId);

        return new OrderItem(quantity, product.getPrice(), observation, product);
    }

}
