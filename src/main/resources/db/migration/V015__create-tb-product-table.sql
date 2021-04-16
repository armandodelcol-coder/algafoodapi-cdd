create table tb_product (
    id bigint not null auto_increment,
    active boolean default false not null,
    name varchar(60) not null,
    description text not null,
    price decimal(10,2) not null,
    restaurant_id bigint not null,

    primary key (id),
    constraint fk_product_restaurant foreign key (restaurant_id) references tb_restaurant (id)
) engine=InnoDB default charset=utf8;