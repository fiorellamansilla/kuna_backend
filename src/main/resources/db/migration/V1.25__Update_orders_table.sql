
ALTER TABLE orders
    ADD COLUMN shipping_detail_id INT NOT NULL,
    ADD CONSTRAINT fk_orders_shipping_detail
    FOREIGN KEY (shipping_detail_id)
    REFERENCES shipping_detail(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
