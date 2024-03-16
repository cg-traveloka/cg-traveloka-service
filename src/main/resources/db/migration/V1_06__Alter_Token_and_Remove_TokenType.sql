-- Xóa khóa ngoại từ 'token' đến 'token_type' và 'user'
ALTER TABLE token
    DROP FOREIGN KEY FK8b0fx0cdgxhv1rxjpwe0iv77i;

-- Xóa bảng 'token_type'
DROP TABLE IF EXISTS token_type;



-- Xóa các cột không cần thiết từ bảng 'token'
ALTER TABLE token
    DROP COLUMN status,
    DROP COLUMN description,
    DROP COLUMN expired_time,
    DROP COLUMN token_type_id,
    DROP COLUMN created_time;


-- Thêm các cột mới vào bảng 'token'
ALTER TABLE token
    ADD refresh_token VARCHAR(255),
    ADD token_issued_at TIMESTAMP,
    ADD token_expired_at TIMESTAMP,
    ADD code_expired_at TIMESTAMP;
