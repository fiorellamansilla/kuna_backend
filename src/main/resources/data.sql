INSERT INTO client (id, password, first_name, last_name, email, role, is_blocked, failed_attempts, created_at, modified_at)
VALUES (1, 'password', 'Mar', 'Chavez', 'mar_chavez@gmail.com', 'CUSTOMER', 0, 0, NOW(), NOW());

INSERT INTO categories (id, name_category, desc_category, image_url)
VALUES (1, 'Shirts', 'Shirts made of organic algodón', 'path_shirt.jpg');

INSERT INTO products (id, name_product, desc_product, price, category_id, image_url, created_at, modified_at)
VALUES (1, 'Classic Shirt', 'Classic shirt made for babies', 10.0, 1, 'path_classic.jpg', NOW(), NOW());

INSERT INTO product_variations (id, product_id, size, color, quantity_stock)
VALUES (1, 1, 'BABY', 'BEIGE', 10);

INSERT INTO shipping_detail (client_id, address, zip_code, city, country, phone, created_at, full_name)
VALUES (1, 'Calle Las Fresias 123', '34394', 'Lima', 'Peru', '+43546455', NOW(), 'Mar Chavez');