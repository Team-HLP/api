CREATE TABLE `mole_catch`
(
    `game_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '고유 게임 ID',
    `score`                  INT UNSIGNED NOT NULL COMMENT '게임 스코어',
    `hp`                     INT UNSIGNED NOT NULL COMMENT '체력',
    `mole_catch_count`      INT UNSIGNED NOT NULL COMMENT '두더지 잡은 수',
    PRIMARY KEY (`game_id`),
    CONSTRAINT FK_MOLE_CATCH_ON_GAME FOREIGN KEY (`game_id`) REFERENCES `games` (`id`)
);
