drop table if exists token;
CREATE TABLE token
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    code             VARCHAR(255),
    refresh_token    VARCHAR(255),
    token_issued_at  TIMESTAMP,
    token_expired_at TIMESTAMP,
    code_expired_at  TIMESTAMP NOT NULL,
    user_email       VARCHAR(255),
    FOREIGN KEY (user_email) REFERENCES User (email)
);
