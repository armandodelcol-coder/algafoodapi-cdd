create table tb_order (
    id bigint not null auto_increment,
    code varchar(36) not null,
    subtotal decimal(10,2) not null,
    delivery_tax decimal(10,2) not null,
    total decimal(10,2) not null,
    created_at datetime not null,
    confirmed_at datetime null,
    delivery_at datetime null,
    canceled_at datetime null,
    status varchar(10) not null,
    restaurant_id bigint not null,
    payment_method_id bigint not null,
    client_id bigint not null,

    address_place varchar(100) not null,
    address_complement varchar(60) null,
    address_neighborhood varchar(60) not null,
    address_number varchar(20) not null,
    address_zipcode varchar(9) not null,
    address_city_id bigint not null,

    primary key (id),
    constraint uk_order_code unique (code),
    constraint fk_order_restaurant foreign key (restaurant_id) references tb_restaurant (id),
    constraint fk_order_payment_method foreign key (payment_method_id) references tb_payment_method (id),
    constraint fk_order_client foreign key (client_id) references tb_user (id),
    constraint fk_order_address_city foreign key (address_city_id) references tb_city (id)
) engine=InnoDB default charset=utf8;

create table tb_order_item (
    id bigint not null auto_increment,
    quantity smallint(6) not null,
    price decimal(10,2) not null,
    total decimal(10,2) not null,
    observation varchar(255) null,
    product_id bigint not null,
    order_id bigint not null,

    primary key (id),
    unique key uk_order_item_product (order_id, product_id),
    constraint fk_order_item_product foreign key (product_id) references tb_product (id),
    constraint fk_order_item_order foreign key (order_id) references tb_order (id)
) engine=InnoDB default charset=utf8;