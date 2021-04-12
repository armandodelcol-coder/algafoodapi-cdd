create table tb_group_permission (
    group_id bigint not null,
    permission_id bigint not null,

    constraint fk_group_permission_group foreign key (group_id) references tb_group (id),
    constraint fk_group_permission_permission foreign key (permission_id) references tb_permission (id)
) engine=InnoDB default charset=utf8;