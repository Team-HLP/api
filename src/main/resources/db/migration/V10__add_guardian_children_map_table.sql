CREATE TABLE `guardian_children_map` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `guardian_id` INT UNSIGNED NOT NULL,
    `children_id` INT UNSIGNED NOT NULL,
    `created_at`  timestamp  default CURRENT_TIMESTAMP not null comment '생성 일자',
    `updated_at`  timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '업데이트 일자',
    CONSTRAINT fk_guardian_children_map_guardian FOREIGN KEY (guardian_id) REFERENCES guardians(id),
    CONSTRAINT fk_guardian_children_map_children FOREIGN KEY (children_id) REFERENCES users(id)
);
