
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+02:00";


CREATE TABLE IF NOT EXISTS `notes` (
    `id` int(10) UNSIGNED NOT NULL,
    `meeting_id` int(10) UNSIGNED NOT NULL,
    `user_id` int(10) UNSIGNED NOT NULL,
    `text` TEXT CHARACTER SET utf8 COLLATE utf8_slovak_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;


CREATE TABLE IF NOT EXISTS `meeting_activity` (
    `id` int(10) UNSIGNED NOT NULL,
    `meeting_id` int(10) UNSIGNED NOT NULL,
    `activity_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

CREATE TABLE IF NOT EXISTS `meeting_user` (
    `id` int(10) UNSIGNED NOT NULL,
    `meeting_id` int(10) UNSIGNED NOT NULL,
    `user_id` int(10) UNSIGNED NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

ALTER TABLE `notes`
    ADD PRIMARY KEY (`id`),
    ADD KEY `notes_ibfk_1` (`meeting_id`),
    ADD KEY `notes_ibfk_2` (`user_id`);

ALTER TABLE `meeting_activity`
    ADD PRIMARY KEY (`id`),
    ADD KEY `meeting_activity_ibfk_1` (`meeting_id`),
    ADD KEY `meeting_activity_ibfk_2` (`activity_id`);

ALTER TABLE `meeting_user`
    ADD PRIMARY KEY (`id`),
    ADD KEY `meeting_user_ibfk_1` (`meeting_id`),
    ADD KEY `meeting_user_ibfk_2` (`user_id`);

ALTER TABLE `notes`
    ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `meeting_activity`
    ADD CONSTRAINT `meeting_activity_ibfk_1` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `meeting_activity_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `meeting_user`
    ADD CONSTRAINT `meeting_user_ibfk_1` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `meeting_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `agenda_points`
    DROP COLUMN `duration`,
    ADD COLUMN `start` datetime DEFAULT NULL,
    ADD COLUMN `actual_start` datetime DEFAULT NULL,
    ADD COLUMN `end` datetime DEFAULT NULL,
    ADD COLUMN `actual_end` datetime DEFAULT NULL;

ALTER TABLE `meetings`
    ADD COLUMN `running_activity_id` int(10) UNSIGNED DEFAULT NULL,
    ADD KEY `meeting_runact_ibfk_1` (`running_activity_id`),
    ADD CONSTRAINT `meeting_runact_ibfk_1` FOREIGN KEY (`running_activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    MODIFY `start` datetime,
    MODIFY `end` datetime,
    MODIFY `actual_start` datetime,
    MODIFY `actual_end` datetime;

    COMMIT;
