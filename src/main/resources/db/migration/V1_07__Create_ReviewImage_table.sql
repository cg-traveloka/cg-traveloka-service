create table review_image(
    id int primary key auto_increment,
    url varchar(255) not null,
    review_id int not null,
    foreign key (review_id) references hotel_review(id)
);