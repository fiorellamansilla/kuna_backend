ALTER TABLE client
    ADD COLUMN failed_attempts INT AFTER is_blocked;