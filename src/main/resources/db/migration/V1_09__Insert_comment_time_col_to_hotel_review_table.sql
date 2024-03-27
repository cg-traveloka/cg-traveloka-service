alter table hotel_review
add comment_time datetime not null default now();

SET FOREIGN_KEY_CHECKS = 1;