ALTER TABLE order_item
    DROP FOREIGN KEY order_item_ibfk_2;

ALTER TABLE order_item
    DROP COLUMN item_id;

ALTER TABLE order_item
    ADD COLUMN product_id INT NOT NULL AFTER order_id;

ALTER TABLE order_item
    ADD CONSTRAINT fk_order_item_product
    FOREIGN KEY (product_id)
    REFERENCES products (id);
