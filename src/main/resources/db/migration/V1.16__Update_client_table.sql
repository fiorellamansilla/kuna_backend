ALTER TABLE client MODIFY address VARCHAR(128) DEFAULT NULL;
ALTER TABLE client MODIFY zip_code VARCHAR(64) DEFAULT NULL;
ALTER TABLE client MODIFY city VARCHAR(32) DEFAULT NULL;
ALTER TABLE client MODIFY country VARCHAR(32) DEFAULT NULL;
ALTER TABLE client MODIFY phone VARCHAR(32) DEFAULT NULL;