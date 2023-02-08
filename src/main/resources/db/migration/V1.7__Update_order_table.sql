
ALTER TABLE orders
    RENAME COLUMN amount TO total_amount;

ALTER TABLE orders
DROP COLUMN first_name,
DROP COLUMN last_name,
DROP COLUMN address,
DROP COLUMN city,
DROP COLUMN zip_code,
DROP COLUMN country,
DROP COLUMN phone,
DROP COLUMN email,
DROP COLUMN ordered_at,
DROP COLUMN shipped_at,
DROP COLUMN tracking_number;

ALTER TABLE orders
    ADD COLUMN payment_id INT NOT NULL,
    ADD COLUMN quantity_ordered INT NOT NULL,
    ADD COLUMN order_status VARCHAR(64) NOT NULL,
    ADD COLUMN tracking_number VARCHAR(64) NOT NULL,
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;




