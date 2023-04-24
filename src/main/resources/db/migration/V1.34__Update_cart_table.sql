ALTER TABLE cart
    DROP FOREIGN KEY fk_cart_product;

ALTER TABLE cart
    DROP COLUMN product_id;

ALTER TABLE cart
    ADD COLUMN product_variation_id INT NOT NULL AFTER client_id;

ALTER TABLE cart
    ADD CONSTRAINT fk_cart_product_variation
    FOREIGN KEY (product_variation_id)
    REFERENCES product_variations (id);