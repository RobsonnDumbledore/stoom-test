CREATE TABLE brand (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT true
);

CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT true
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    active BOOLEAN DEFAULT true,
    brand_id INTEGER
);

CREATE TABLE product_category (
    product_id INTEGER,
    category_id INTEGER,
    PRIMARY KEY (product_id, category_id)
);