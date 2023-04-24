ALTER TABLE cart
    DROP FOREIGN KEY cart_ibfk_2;

ALTER TABLE cart
    DROP COLUMN item_id;

ALTER TABLE cart
    ADD COLUMN product_id INT NOT NULL AFTER client_id;

ALTER TABLE cart
    ADD CONSTRAINT fk_cart_product
    FOREIGN KEY (product_id)
    REFERENCES products (id);