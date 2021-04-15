create table tb_user_group (
    user_id bigint not null,
    group_id bigint not null,

    constraint fk_user_group_user foreign key (user_id) references tb_user (id),
    constraint fk_user_group_group foreign key (group_id) references tb_group (id)
) engine=InnoDB default charset=utf8;