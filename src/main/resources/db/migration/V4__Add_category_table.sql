
CREATE TABLE category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name_category VARCHAR(128) NOT NULL,
    desc_category VARCHAR(256),
    image_url VARCHAR (256),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);