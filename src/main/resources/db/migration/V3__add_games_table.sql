CREATE TABLE `games`
(
    `id`                     INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '고유 게임 ID',
    `user_id`                INT UNSIGNED NOT NULL COMMENT '고유 유저 ID',
    `result`                 VARCHAR(10)  NOT NULL COMMENT '게임 결과',
    `score`                  INT UNSIGNED NOT NULL COMMENT '게임 스코어',
    `hp`                     INT UNSIGNED NOT NULL COMMENT '체력',
    `meteorite_broken_count` INT UNSIGNED NOT NULL COMMENT '운석 파괴 수',
    `blink_eye_count`        INT UNSIGNED NOT NULL COMMENT '총 눈 깜빡임 수',
    `avg_left_eye_pupil_size`  FLOAT NOT NULL COMMENT '왼쪽 눈 동공 크기',
    `avg_right_eye_pupil_size` FLOAT NOT NULL COMMENT '오른쪽 눈 동공 크기',
    `created_at`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성 일자',
    `updated_at`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 일자',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
