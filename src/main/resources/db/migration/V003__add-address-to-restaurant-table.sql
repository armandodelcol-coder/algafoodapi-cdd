alter table tb_restaurant add column address_place varchar(100) not null;
alter table tb_restaurant add column address_complement varchar(60);
alter table tb_restaurant add column address_neighborhood varchar(60) not null;
alter table tb_restaurant add column address_number varchar(20) not null;
alter table tb_restaurant add column address_zipcode varchar(9) not null;