CREATE TABLE token
(
        id int primary key auto_increment,
        refresh_token VARCHAR(255),
        token_issued_at TIMESTAMP,
        token_expired_at TIMESTAMP,
        code_expired_at TIMESTAMP,
        code VARCHAR(255),
        email VARCHAR(255),
        foreign key (email) references user (email)
)