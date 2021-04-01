CREATE TABLE tb_restaurant (
    id bigint not null auto_increment,
    name varchar(60) not null,
    delivery_tax decimal(10, 2),
    created_at datetime not null,
    updated_at datetime not null,
    active tinyint(1) not null default false,
    open tinyint(1) not null default false,

    kitchen_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

alter table tb_restaurant add constraint fk_restaurant_kitchen foreign key (kitchen_id) references tb_kitchen (id);