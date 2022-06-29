
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+02:00";

ALTER TABLE `attendances`
    ADD COLUMN `last_joined_at` datetime DEFAULT NULL;

ALTER TABLE  `meetings`
    MODIFY `feedback_type` enum('none','general','specific') COLLATE utf8_slovak_ci NOT NULL;

ALTER TABLE `agenda_points`
    MODIFY COLUMN `status` enum('PENDING','ONGOING', 'DONE', 'SKIPPED') COLLATE utf8_slovak_ci NOT NULL DEFAULT 'PENDING';
    COMMIT;
