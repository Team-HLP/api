CREATE TABLE `admins`
(
    `id`           INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '고유 어드민 ID',
    `login_id`     VARCHAR(50) NOT NULL COMMENT '로그인 ID',
    `password`     MEDIUMTEXT  NOT NULL COMMENT '비밀번호',
    `is_auth`      TINYINT(1)  NOT NULL COMMENT '인증 상태',
    `name`         VARCHAR(20) NOT NULL COMMENT '이름',
    `phone_number` VARCHAR(20) NOT NULL COMMENT '전화번호',
    `created_at`  timestamp  default CURRENT_TIMESTAMP not null comment '생성 일자',
    `updated_at`  timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '업데이트 일자',
    PRIMARY KEY (`id`)
)
