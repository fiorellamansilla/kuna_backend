ALTER TABLE order_item
    ADD COLUMN price DOUBLE PRECISION NOT NULL,
    ADD COLUMN quantity INT NOT NULL;
