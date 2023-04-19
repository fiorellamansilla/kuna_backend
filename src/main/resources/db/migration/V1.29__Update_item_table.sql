RENAME TABLE item TO products;

ALTER TABLE products
    DROP COLUMN size,
    DROP COLUMN color,
    DROP COLUMN discount,
    DROP COLUMN SKU,
    DROP COLUMN quantity_stock,
    DROP COLUMN deleted_at,
    DROP COLUMN category;

ALTER TABLE products
    CHANGE COLUMN name_item name_product VARCHAR(128) NOT NULL,
    CHANGE COLUMN desc_item desc_product VARCHAR(2048) NOT NULL,
    CHANGE COLUMN image_path image_url VARCHAR(256) NOT NULL;

ALTER TABLE products
    ADD COLUMN category_id INT NOT NULL,
    ADD CONSTRAINT fk_products_category
    FOREIGN KEY (category_id)
    REFERENCES categories (id);

