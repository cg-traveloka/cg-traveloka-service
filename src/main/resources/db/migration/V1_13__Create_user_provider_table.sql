DROP TABLE IF EXISTS user_provider;

CREATE TABLE user_provider
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_email  VARCHAR(255),
    provider_id BIGINT,
    FOREIGN KEY (user_email) REFERENCES user (email),
    FOREIGN KEY (provider_id) REFERENCES provider (id)
);
