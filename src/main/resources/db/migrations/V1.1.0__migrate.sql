START TRANSACTION;
SET timezone TO = "+02:00";

DROP TABLE IF EXISTS `attendees`;

CREATE TABLE IF NOT EXISTS `attendances` (
    `id` SERIAL PRIMARY KEY,
    `feedback_rating` int DEFAULT NULL,
    `feedback_comment` varchar(256) DEFAULT NULL,
    `participation` tinyint(1) NOT NULL DEFAULT '0',
    `meeting_id` int NOT NULL,
    `user_id` int NOT NULL,
    `last_joined_at` timestamp DEFAULT NULL,
    `presence_time` int DEFAULT '0'
    CONSTRAINT attendances_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id)
    CONSTRAINT attendances_ibfk_2
        FOREIGN KEY(user_id)
            REFERENCES users(id)
    );

--
CREATE TABLE IF NOT EXISTS `notes` (
    `id` SERIAL PRIMARY KEY,
    `meeting_id` int(10) UNSIGNED NOT NULL,
    `user_id` int(10) UNSIGNED NOT NULL,
    `text` TEXT NOT NULL,
    CONSTRAINT notes_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id)
    CONSTRAINT notes_ibfk_2
        FOREIGN KEY(user_id)
            REFERENCES users(id)
    );


CREATE TABLE IF NOT EXISTS `meeting_activity` (
    `id` SERIAL PRIMARY KEY,
    `meeting_id` int(10) NOT NULL,
    `activity_id` int(10) NOT NULL,
    CONSTRAINT meeting_activity_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id)
    CONSTRAINT meeting_activity_ibfk_2
        FOREIGN KEY(activity_id)
            REFERENCES activities(id)
    );

ALTER TABLE `agenda_points`
    ALTER COLUMN `description` TEXT;

CREATE TYPE feedback_type AS ENUM ('none','general','specific');

ALTER TABLE `meetings`
    ADD COLUMN `apollo_code` VARCHAR(256) DEFAULT NULL,
    ADD COLUMN `feedback_type` feedback_type NOT NULL;

ALTER TABLE `agenda_points`
    DROP COLUMN `duration`,
    ADD COLUMN `start` timestamp DEFAULT NULL,
    ADD COLUMN `actual_start` timestamp DEFAULT NULL,
    ADD COLUMN `end` timestamp DEFAULT NULL,
    ADD COLUMN `actual_end` timestamp DEFAULT NULL;

ALTER TABLE `meetings`
    ADD COLUMN `running_activity_id` int(10) DEFAULT NULL,
    ADD KEY `meeting_runact_ibfk_1` (`running_activity_id`),
    ADD CONSTRAINT `meeting_runact_ibfk_1` FOREIGN KEY (`running_activity_id`) REFERENCES `activities` (`id`),
    MODIFY `start` timestamp,
    MODIFY `end` timestamp,
    MODIFY `actual_start` timestamp,
    MODIFY `actual_end` timestamp;

COMMIT;