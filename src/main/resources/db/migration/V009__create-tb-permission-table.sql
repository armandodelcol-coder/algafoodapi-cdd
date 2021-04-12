CREATE TABLE tb_permission (
    id bigint not null auto_increment,
    name varchar(60) not null,
    description text not null,

    primary key (id)
) engine=InnoDB default charset=utf8;