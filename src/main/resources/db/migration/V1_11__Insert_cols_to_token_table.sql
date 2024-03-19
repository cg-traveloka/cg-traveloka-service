ALTER TABLE token
    ADD refresh_token VARCHAR(255),
    ADD token_issued_at TIMESTAMP,
    ADD token_expired_at TIMESTAMP,
    ADD code_expired_at TIMESTAMP;