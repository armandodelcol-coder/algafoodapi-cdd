create table tb_restaurant_responsible_user (
    restaurant_id bigint not null,
    user_id bigint not null
) engine=InnoDB default charset=utf8;

alter table tb_restaurant_responsible_user add constraint fk_rest_responsible_user_user foreign key (user_id) references tb_user (id);
alter table tb_restaurant_responsible_user add constraint fk_rest_responsible_user_rest foreign key (restaurant_id) references tb_restaurant (id);