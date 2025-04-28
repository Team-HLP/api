CREATE TABLE `guardians`
(
    `id`           INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '고유 사용자 ID',
    `login_id`     VARCHAR(50) NOT NULL COMMENT '로그인 ID',
    `password`     MEDIUMTEXT  NOT NULL COMMENT '비밀번호',
    `name`         VARCHAR(20) NOT NULL COMMENT '이름',
    `phone_number` VARCHAR(20) NOT NULL COMMENT '전화번호',
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제 여부',
    `created_at`  timestamp  default CURRENT_TIMESTAMP not null comment '생성 일자',
    `updated_at`  timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '업데이트 일자',
    PRIMARY KEY (`id`)
)
