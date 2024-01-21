-- BRAND
CREATE INDEX idx_brand_name ON brand(name);
CREATE INDEX idx_brand_active ON brand(active);

-- PRODUCT
CREATE INDEX idx_product_name ON product(name);
CREATE INDEX idx_product_active ON product(active);

-- CATEGORY
CREATE INDEX idx_category_name ON category(name);
CREATE INDEX idx_category_active ON category(active);
