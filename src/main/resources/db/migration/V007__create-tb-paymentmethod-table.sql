CREATE TABLE tb_payment_method (
    id bigint not null auto_increment,
    description varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;