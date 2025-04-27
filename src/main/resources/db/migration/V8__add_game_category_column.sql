ALTER TABLE games
    ADD COLUMN `game_category` VARCHAR(50) NOT NULL COMMENT '게임 카테고리';

ALTER TABLE games
    ADD COLUMN `base_left_pupil_size` FLOAT NOT NULL COMMENT '왼쪽 기준 동공 크기';

ALTER TABLE games
    ADD COLUMN `below_base_left_pupil_count` INT UNSIGNED NOT NULL COMMENT '왼쪽 기준 동공 크기보다 낮아진 횟수';

ALTER TABLE games
    ADD COLUMN `base_right_pupil_size` FLOAT NOT NULL COMMENT '오른쪽 기준 동공 크기';

ALTER TABLE games
    ADD COLUMN `below_base_right_pupil_count` INT UNSIGNED NOT NULL COMMENT '오른쪽 기준 동공 크기보다 낮아진 횟수';
