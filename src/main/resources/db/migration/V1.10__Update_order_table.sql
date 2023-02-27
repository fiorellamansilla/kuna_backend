ALTER TABLE orders
DROP COLUMN quantity_ordered,
DROP COLUMN payment_id;

ALTER TABLE orders
    ADD COLUMN session_id VARCHAR(256) NOT NULL;