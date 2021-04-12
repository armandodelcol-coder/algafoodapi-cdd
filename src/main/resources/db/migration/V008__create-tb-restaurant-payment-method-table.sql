create table tb_restaurant_payment_method (
    restaurant_id bigint not null,
    payment_method_id bigint not null
) engine=InnoDB default charset=utf8;

alter table tb_restaurant_payment_method add constraint fk_rest_payment_method_pm foreign key (payment_method_id) references tb_payment_method (id);
alter table tb_restaurant_payment_method add constraint fk_rest_payment_method_rest foreign key (restaurant_id) references tb_restaurant (id);