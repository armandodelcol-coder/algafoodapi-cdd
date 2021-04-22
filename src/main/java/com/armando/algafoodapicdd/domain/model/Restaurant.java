package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.api.model.request.RestaurantRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

// Carga intrínsica = 8; Limite = 9
@Entity
@Table(name = "tb_restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal deliveryTax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
    // Carga: +1 (Kitchen)
    private Kitchen kitchen;

    private Boolean active = Boolean.FALSE;

    private Boolean open = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

    @Embedded
    // Carga: +1 (Address)
    private Address address;

    @ManyToMany
    @JoinTable(name = "tb_restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    // Carga: +1 (PaymentMethod)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tb_restaurant_responsible_user",
            joinColumns = @JoinColumn(name = "restaurant_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    // Carga: +1 (User)
    private Set<User> responsible = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    // Carga: +1 (Product)
    private Set<Product> products = new HashSet<>();

    @Deprecated
    public Restaurant() {
    }

    public Restaurant(String name, BigDecimal deliveryTax, Kitchen kitchen, Address address) {
        this.name = name;
        this.deliveryTax = deliveryTax;
        this.kitchen = kitchen;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryTax() {
        return deliveryTax;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getOpen() {
        return open;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Address getAddress() {
        return address;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public Set<User> getResponsible() {
        return responsible;
    }

    public Set<Product> getProducts() {
        return products;
    }

    // Carga: +1 (RestaurantRequest)
    public void setPropertiesToUpdate(RestaurantRequest restaurantRequest, EntityManager manager) {
        Assert.state(restaurantRequest != null, "Não é permitido informar um objeto restaurantRequest nulo para atualizar os dados de restaurante.");

        Kitchen kitchen = manager.find(Kitchen.class, restaurantRequest.getKitchenId());
        Assert.state(kitchen != null, "Não é permitido informar uma cozinha inexistente para atualizar os dados de restaurante.");

        // Carga: +1 (City)
        City city = manager.find(City.class, restaurantRequest.getAddress().getCityId());
        Assert.state(city != null, "Não é permitido informar uma cidade inexistente para atualizar os dados de restaurante.");

        this.name = restaurantRequest.getName();
        this.deliveryTax = restaurantRequest.getDeliveryTax();
        this.kitchen = kitchen;
        this.address = new Address(
                restaurantRequest.getAddress().getZipcode(),
                restaurantRequest.getAddress().getPlace(),
                restaurantRequest.getAddress().getNumber(),
                restaurantRequest.getAddress().getComplement(),
                restaurantRequest.getAddress().getNeighborhood(),
                city
        );
    }

    public void associatePaymentMethod(PaymentMethod paymentMethod) {
        Assert.state(paymentMethod != null, "Está tentando associar uma forma de pagamento no restaurante passando um valor nulo como parâmetro.");

        this.paymentMethods.add(paymentMethod);
    }

    public void dissociatePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.remove(paymentMethod);
    }

    public boolean hasThisResponsibleById(Long userId) {
        return responsible.stream().filter(user -> user.getId().equals(userId)).findAny().isPresent();
    }

    public void associateResponsible(User user) {
        responsible.add(user);
    }

    public void dissociateResponsible(User user) {
        responsible.remove(user);
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public void openUp() {
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean hasPaymentMethodById(Long paymentMethodId) {
        // Carga: +1 (função como argumento)
        return paymentMethods.stream().filter(paymentMethod -> paymentMethod.getId().equals(paymentMethodId)).findAny().isPresent();
    }

    public boolean acceptPaymentMethod(PaymentMethod paymentMethod) {
        return this.paymentMethods.contains(paymentMethod);
    }

}
