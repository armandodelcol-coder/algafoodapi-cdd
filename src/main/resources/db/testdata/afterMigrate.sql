set foreign_key_checks = 0;

delete from tb_kitchen;
delete from tb_restaurant;

set foreign_key_checks = 1;

alter table tb_kitchen auto_increment = 1;
alter table tb_restaurant auto_increment = 1;

INSERT INTO tb_kitchen (name) VALUES ('Tailandesa');
INSERT INTO tb_kitchen (name) VALUES ('Indiana');
INSERT INTO tb_kitchen (name) VALUES ('Brasileira');
INSERT INTO tb_kitchen (name) VALUES ('Americana');
INSERT INTO tb_kitchen (name) VALUES ('Chinesa');
INSERT INTO tb_kitchen (name) VALUES ('Mexicana');

INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Thai Food", 2.99, utc_timestamp, utc_timestamp, 1);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Caminho das Indias", 5, utc_timestamp, utc_timestamp, 2);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Lyla's Restaurante", 4, utc_timestamp, utc_timestamp, 3);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Mc Soda", 4, utc_timestamp, utc_timestamp, 3);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Feijoadassa", 1, utc_timestamp, utc_timestamp, 3);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Burguer King", 9, utc_timestamp, utc_timestamp, 4);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("China In Box", 7, utc_timestamp, utc_timestamp, 5);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id) VALUES ("Tex&Mex", 12, utc_timestamp, utc_timestamp, 6);