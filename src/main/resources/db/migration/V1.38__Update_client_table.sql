ALTER TABLE client MODIFY failed_attempts INT DEFAULT 0 AFTER is_blocked;
