ALTER TABLE shipping_detail
DROP FOREIGN KEY shipping_detail_ibfk_2;

ALTER TABLE shipping_detail
    DROP COLUMN order_id;
