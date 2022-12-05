USE kuna_db;

CREATE TABLE  orders (
    order_id INT NOT NULL AUTO_INCREMENT,
    client_id INT NOT NULL,
    order_amount FLOAT NOT NULL DEFAULT 0,
    ship_name VARCHAR(128) NOT NULL,
    ship_address VARCHAR(2048) NOT NULL,
    order_city VARCHAR(32) NOT NULL,
    order_zip VARCHAR(64) NOT NULL,
    order_country VARCHAR(32) NOT NULL,
    order_phone VARCHAR(32) NOT NULL,
    order_email VARCHAR(64) NOT NULL,
    ordered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    shipped_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tracking_number VARCHAR(64) NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE CASCADE ON UPDATE CASCADE
);