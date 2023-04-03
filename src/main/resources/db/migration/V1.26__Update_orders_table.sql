ALTER TABLE orders
    ADD COLUMN payment_id INT NOT NULL,
    ADD CONSTRAINT fk_orders_payment
    FOREIGN KEY (payment_id)
    REFERENCES payment(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;