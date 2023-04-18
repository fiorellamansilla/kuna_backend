ALTER TABLE payment
DROP FOREIGN KEY payment_ibfk_2;

ALTER TABLE payment
    DROP COLUMN order_id;