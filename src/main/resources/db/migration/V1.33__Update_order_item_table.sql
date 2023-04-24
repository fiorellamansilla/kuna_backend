ALTER TABLE order_item
    DROP COLUMN product_id;

ALTER TABLE order_item
    ADD COLUMN product_variation_id INT NOT NULL AFTER order_id;

ALTER TABLE order_item
    ADD CONSTRAINT fk_order_item_product_variation
    FOREIGN KEY (product_variation_id)
    REFERENCES product_variations (id);