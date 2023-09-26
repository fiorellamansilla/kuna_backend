RENAME TABLE item TO products;

ALTER TABLE products
    ADD COLUMN category_id INT NOT NULL AFTER price;

ALTER TABLE products
    ADD CONSTRAINT fk_products_category
    FOREIGN KEY (category_id)
    REFERENCES categories (id);

