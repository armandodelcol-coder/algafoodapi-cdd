CREATE TABLE tb_city (
    id bigint not null auto_increment,
    name varchar(60) not null,

    state_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

alter table tb_city add constraint fk_city_state foreign key (state_id) references tb_state (id);