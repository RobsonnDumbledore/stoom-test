-- Inserindo marcas
INSERT INTO brand (name) VALUES ('Marca01');
INSERT INTO brand (name) VALUES ('Marca02');
INSERT INTO brand (name) VALUES ('Marca03');

-- Inserindo categorias
INSERT INTO category (name) VALUES ('ELETRONICOS');
INSERT INTO category (name) VALUES ('ROUPAS');
INSERT INTO category (name) VALUES ('ACESSORIOS');

-- Inserindo produtos
INSERT INTO product (name, description, price, brand_id) VALUES ('Produto 1', 'Descricao do produto 1', 99.99, 1);
INSERT INTO product (name, description, price, brand_id) VALUES ('Produto 2', 'Descricao do produto 2', 199.99, 2);
INSERT INTO product (name, description, price, brand_id) VALUES ('Produto 3', 'Descricao do Produto 3', 299.99, 3);

-- Associando produtos às categorias
INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO product_category (product_id, category_id) VALUES (3, 3);
