
CREATE TABLE orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    shipping_detail_id BIGINT NOT NULL,
    payment_id BIGINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    order_status VARCHAR(64) NOT NULL,
    tracking_number VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (shipping_detail_id) REFERENCES shipping_detail (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES payment (id) ON DELETE CASCADE ON UPDATE CASCADE
);