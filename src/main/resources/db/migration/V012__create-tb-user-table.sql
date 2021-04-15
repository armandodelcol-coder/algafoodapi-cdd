create table tb_user (
    id bigint not null auto_increment,
    name varchar(60) not null,
    email varchar(255) not null,
    password varchar(20) not null,
    created_at datetime not null,

    primary key (id)
) engine=InnoDB default charset=utf8;