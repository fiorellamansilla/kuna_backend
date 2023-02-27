
CREATE TABLE token (
    id INT NOT NULL AUTO_INCREMENT,
    token VARCHAR(256) NOT NULL,
    client_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE ON UPDATE CASCADE
);