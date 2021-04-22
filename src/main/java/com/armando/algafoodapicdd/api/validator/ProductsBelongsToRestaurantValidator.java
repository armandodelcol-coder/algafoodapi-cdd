package com.armando.algafoodapicdd.api.validator;

import com.armando.algafoodapicdd.api.model.request.OrderRequest;
import com.armando.algafoodapicdd.domain.model.Product;
import com.armando.algafoodapicdd.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductsBelongsToRestaurantValidator implements Validator {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // if (errors.hasErrors()) return;

        List<Long> productIdsInvalids = new ArrayList<>();
        OrderRequest request = (OrderRequest) target;
        request.getItems().forEach(orderItemRequest -> {
            Optional<Product> orderItemProduct = productRepository.findByIdAndRestaurantId(orderItemRequest.getProductId(), request.getRestaurantId());
            if (!orderItemProduct.isPresent()) productIdsInvalids.add(orderItemRequest.getProductId());
        });
        if (!productIdsInvalids.isEmpty()) {
            errors.rejectValue(
                    "items",
                    null,
                    "Ids de produtos informados não são de produtos pertencentes ao restaurante informado: " + productIdsInvalids);
        }
    }

}
