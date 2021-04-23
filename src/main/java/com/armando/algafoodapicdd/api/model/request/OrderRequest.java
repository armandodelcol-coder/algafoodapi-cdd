package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.ExistsId;
import com.armando.algafoodapicdd.domain.model.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderRequest {

    @NotNull
    @ExistsId(domainClass = Restaurant.class)
    private Long restaurantId;

    @NotNull
    @ExistsId(domainClass = PaymentMethod.class)
    private Long paymentMethodId;

    @NotNull
    @Valid
    private AddressRequest deliveryAddress;

    @Size(min = 1)
    @NotNull
    @Valid
    private List<OrderItemRequest> items;

    @Deprecated
    public OrderRequest() {
    }

    public OrderRequest(Long restaurantId, Long paymentMethodId, AddressRequest deliveryAddress, List<OrderItemRequest> items) {
        this.restaurantId = restaurantId;
        this.paymentMethodId = paymentMethodId;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public AddressRequest getDeliveryAddress() {
        return deliveryAddress;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public Order toModel(EntityManager manager, User client) {
        City city = manager.find(City.class, this.deliveryAddress.getCityId());
        Assert.notNull(city, "Não é possível criar um Endereço com uma Cidade nula");

        Address address = new Address(
                this.deliveryAddress.getZipcode(),
                this.deliveryAddress.getPlace(),
                this.deliveryAddress.getNumber(),
                this.deliveryAddress.getComplement(),
                this.deliveryAddress.getNeighborhood(),
                city
        );
        Restaurant restaurant = manager.find(Restaurant.class, this.restaurantId);
        Assert.notNull(restaurant, "Não é possível criar um Pedido com um Restaurante nulo");

        PaymentMethod paymentMethod = manager.find(PaymentMethod.class, this.paymentMethodId);
        Assert.notNull(paymentMethod, "Não é possível cirar um Pedido com Método de Pagamento nulo.");

        List<OrderItem> orderItems = this.items.stream().map(orderItemRequest -> orderItemRequest.toModel(manager)).collect(Collectors.toList());
        Order order = new Order(paymentMethod, restaurant, client, address, orderItems);
        order.calcTotal();
        order.addOrderInItems();
        return order;
    }

}
