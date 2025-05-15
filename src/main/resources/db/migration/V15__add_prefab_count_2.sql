ALTER TABLE mole_catch
    ADD COLUMN `meteorite_count` INT UNSIGNED NOT NULL COMMENT '메테오 출현 횟수';

ALTER TABLE mole_catch
    ADD COLUMN `fuel_count` INT UNSIGNED NOT NULL COMMENT '연료 출현 횟수';
