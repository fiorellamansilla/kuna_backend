ALTER TABLE client
    ADD COLUMN role VARCHAR(64) NOT NULL AFTER email;

ALTER TABLE client
    ADD COLUMN is_blocked BOOLEAN DEFAULT 0 AFTER role;



