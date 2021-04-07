alter table tb_restaurant add column address_city_id bigint not null;

alter table tb_restaurant add constraint fk_restaurant_address_city foreign key (address_city_id) references tb_city (id);