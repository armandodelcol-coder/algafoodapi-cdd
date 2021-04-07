set foreign_key_checks = 0;

delete from tb_kitchen;
delete from tb_restaurant;
delete from tb_state;
delete from tb_city;

set foreign_key_checks = 1;

alter table tb_kitchen auto_increment = 1;
alter table tb_restaurant auto_increment = 1;
alter table tb_state auto_increment = 1;
alter table tb_city auto_increment = 1;

INSERT INTO tb_kitchen (name) VALUES ('Tailandesa');
INSERT INTO tb_kitchen (name) VALUES ('Indiana');
INSERT INTO tb_kitchen (name) VALUES ('Brasileira');
INSERT INTO tb_kitchen (name) VALUES ('Americana');
INSERT INTO tb_kitchen (name) VALUES ('Chinesa');
INSERT INTO tb_kitchen (name) VALUES ('Mexicana');

INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Thai Food", 2.99, utc_timestamp, utc_timestamp, 1, "Rua Professor Manoel De Souza", null, "Jardim Primavera", "300", "1198545");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Caminho das Indias", 5, utc_timestamp, utc_timestamp, 2, "Avenida 7", null, "Jardim Central", "115", "12915000");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Lyla's Restaurante", 4, utc_timestamp, utc_timestamp, 3, "Avenida 8", null, "Jardim Maravilha", "200", "12915000");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Mc Soda", 4, utc_timestamp, utc_timestamp, 3, "Avenida 9", null, "Jardim Dom João", "33", "12915000");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Feijoadassa", 1, utc_timestamp, utc_timestamp, 3, "Avenida 17", null, "Centro", "77", "12915000");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Burguer King", 9, utc_timestamp, utc_timestamp, 4, "Avenida 27", null, "Jardim São Miguel", "88", "12915000");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("China In Box", 7, utc_timestamp, utc_timestamp, 5, "Avenida 37", null, "Centro", "100", "12915000");
INSERT INTO tb_restaurant (name, delivery_tax, created_at, updated_at, kitchen_id, address_place, address_complement, address_neighborhood, address_number, address_zipcode) VALUES ("Tex&Mex", 12, utc_timestamp, utc_timestamp, 6, "Avenida 77", null, "São Lourenço", "100-A", "12915000");

INSERT INTO tb_state (name) VALUES ('São Paulo');
INSERT INTO tb_state (name) VALUES ('Minas Gerais');
INSERT INTO tb_state (name) VALUES ('Rio Grande do Sul');

INSERT INTO tb_city (name, state_id) VALUES ('Bragança Paulista', 1);
INSERT INTO tb_city (name, state_id) VALUES ('São Paulo', 1);
INSERT INTO tb_city (name, state_id) VALUES ('Extrema', 2);
INSERT INTO tb_city (name, state_id) VALUES ('Florianópolis', 3);
