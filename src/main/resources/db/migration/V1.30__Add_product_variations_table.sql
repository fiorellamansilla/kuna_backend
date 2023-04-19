
CREATE TABLE product_variations (
    id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    size VARCHAR(64) NOT NULL,
    color VARCHAR(64) NOT NULL,
    quantity_stock INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE
);