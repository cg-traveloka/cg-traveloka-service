CREATE TABLE `cg-traveloka-service`.`room_img`
(
    `id`      INT NOT NULL AUTO_INCREMENT,
    `room_id` INT NULL,
    `url`     VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    INDEX     `room_id_idx` (`room_id` ASC) VISIBLE,
    CONSTRAINT `room_id`
        FOREIGN KEY (`room_id`)
            REFERENCES `cg-traveloka-service`.`room` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);