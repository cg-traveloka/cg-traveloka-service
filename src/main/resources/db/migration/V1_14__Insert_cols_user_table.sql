ALTER TABLE User
    ADD COLUMN username  VARCHAR(255),
    ADD COLUMN phone     VARCHAR(20),
    ADD COLUMN is_active BOOLEAN;
