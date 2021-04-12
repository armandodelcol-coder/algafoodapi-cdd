set foreign_key_checks = 0;

delete from tb_kitchen;
delete from tb_restaurant;
delete from tb_state;
delete from tb_city;
delete from tb_payment_method;
delete from tb_restaurant_payment_method;
delete from tb_permission;
delete from tb_group;

set foreign_key_checks = 1;

alter table tb_kitchen auto_increment = 1;
alter table tb_restaurant auto_increment = 1;
alter table tb_state auto_increment = 1;
alter table tb_city auto_increment = 1;
alter table tb_payment_method auto_increment = 1;
alter table tb_permission auto_increment = 1;
alter table tb_group auto_increment = 1;

INSERT INTO tb_kitchen (name) VALUES ('Tailandesa');
INSERT INTO tb_kitchen (name) VALUES ('Indiana');
INSERT INTO tb_kitchen (name) VALUES ('Brasileira');
INSERT INTO tb_kitchen (name) VALUES ('Americana');
INSERT INTO tb_kitchen (name) VALUES ('Chinesa');
INSERT INTO tb_kitchen (name) VALUES ('Mexicana');

INSERT INTO tb_state (name) VALUES ('São Paulo');
INSERT INTO tb_state (name) VALUES ('Minas Gerais');
INSERT INTO tb_state (name) VALUES ('Rio Grande do Sul');

INSERT INTO tb_city (name, state_id) VALUES ('Bragança Paulista', 1);
INSERT INTO tb_city (name, state_id) VALUES ('São Paulo', 1);
INSERT INTO tb_city (name, state_id) VALUES ('Extrema', 2);
INSERT INTO tb_city (name, state_id) VALUES ('Florianópolis', 3);

INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Thai Food", 2.99, utc_timestamp, utc_timestamp, 1, "Rua Professor Manoel De Souza", null, "Jardim Primavera", "300", "1198545", 1);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Caminho das Indias", 5, utc_timestamp, utc_timestamp, 2, "Avenida 7", null, "Jardim Central", "115", "12915000", 2);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Lyla's Restaurante", 4, utc_timestamp, utc_timestamp, 3, "Avenida 8", null, "Jardim Maravilha", "200", "12915000", 3);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Mc Soda", 4, utc_timestamp, utc_timestamp, 3, "Avenida 9", null, "Jardim Dom João", "33", "12915000", 4);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Feijoadassa", 1, utc_timestamp, utc_timestamp, 3, "Avenida 17", null, "Centro", "77", "12915000", 1);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Burguer King", 9, utc_timestamp, utc_timestamp, 4, "Avenida 27", null, "Jardim São Miguel", "88", "12915000", 2);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("China In Box", 7, utc_timestamp, utc_timestamp, 5, "Avenida 37", null, "Centro", "100", "12915000", 3);
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode, address_city_id) VALUES ("Tex&Mex", 12, utc_timestamp, utc_timestamp, 6, "Avenida 77", null, "São Lourenço", "100-A", "12915000", 4);

INSERT INTO tb_payment_method (description) VALUES ('Dinheiro');
INSERT INTO tb_payment_method (description) VALUES ('Cartão de Débito');
INSERT INTO tb_payment_method (description) VALUES ('Cartão de Crédito');

INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 2);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 3);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (2, 2);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (2, 3);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (3, 1);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (4, 3);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (5, 3);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (6, 1);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (7, 2);
INSERT INTO tb_restaurant_payment_method (restaurant_id, payment_method_id) VALUES (8, 3);

INSERT INTO tb_permission (name, description) VALUES ('Administrador Geral', 'Permite realizar todas a operações do sistema.');
INSERT INTO tb_permission (name, description) VALUES ('Leitura Geral', 'Permite realizar leitura de todas as tabelas do sistema.');
INSERT INTO tb_permission (name, description) VALUES ('Criar cozinhas', 'Permite criar novas cozinhas');
INSERT INTO tb_permission (name, description) VALUES ('Controlador cozinhas', 'Permite criar, atualizar, deletar e visualizar cozinhas');

INSERT INTO tb_group(name) VALUES ('Grupo 1');
INSERT INTO tb_group(name) VALUES ('Grupo 2');