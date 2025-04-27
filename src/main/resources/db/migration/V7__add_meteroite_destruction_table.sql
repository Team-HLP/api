CREATE TABLE `meteroite_destruction`
(
    `game_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '고유 게임 ID',
    `score`                  INT UNSIGNED NOT NULL COMMENT '게임 스코어',
    `hp`                     INT UNSIGNED NOT NULL COMMENT '체력',
    `meteorite_broken_count` INT UNSIGNED NOT NULL COMMENT '운석 파괴 수',
    PRIMARY KEY (`game_id`),
    CONSTRAINT FK_METEROITE_ON_GAME FOREIGN KEY (`game_id`) REFERENCES `games` (`id`)
);
