
CREATE TABLE shipping_detail (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    full_name VARCHAR(64) NOT NULL,
    address VARCHAR(128) NOT NULL,
    zip_code VARCHAR(64) NOT NULL,
    city VARCHAR (32) NOT NULL,
    country VARCHAR (32) NOT NULL,
    phone VARCHAR (32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE ON UPDATE CASCADE
);