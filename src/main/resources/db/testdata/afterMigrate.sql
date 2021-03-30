set foreign_key_checks = 0;

delete from tb_kitchen;

set foreign_key_checks = 1;

alter table tb_kitchen auto_increment = 1;

INSERT INTO tb_kitchen (name) VALUES ('Tailandesa');
INSERT INTO tb_kitchen (name) VALUES ('Indiana');
INSERT INTO tb_kitchen (name) VALUES ('Brasileira');
INSERT INTO tb_kitchen (name) VALUES ('Americana');
INSERT INTO tb_kitchen (name) VALUES ('Chinesa');
INSERT INTO tb_kitchen (name) VALUES ('Mexicana');