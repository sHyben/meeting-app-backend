
/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
SET timezone TO "+02:00";
-- My Changes
DROP TABLE IF EXISTS attendees;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE attendances_seq;

CREATE TABLE IF NOT EXISTS attendances (
                                           id int CHECK (id > 0) NOT NULL DEFAULT NEXTVAL ('attendances_seq'),
                                           feedback_rating int DEFAULT NULL,
                                           feedback_comment varchar(256) CHARACTER SET utf8 DEFAULT NULL,
                                           participation smallint NOT NULL DEFAULT '0',
                                           meeting_id int CHECK (meeting_id > 0) NOT NULL,
                                           user_id int CHECK (user_id > 0) NOT NULL,
                                           last_joined_at timestamp(0) DEFAULT NULL,
                                           presence_time int DEFAULT '0',
                                           primary key (id)
)  ;

--
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE notes_seq;

CREATE TABLE IF NOT EXISTS notes (
                                     id int CHECK (id > 0) NOT NULL DEFAULT NEXTVAL ('notes_seq'),
                                     meeting_id int CHECK (meeting_id > 0) NOT NULL,
                                     user_id int CHECK (user_id > 0) NOT NULL,
                                     "text" TEXT CHARACTER SET utf8 NOT NULL,
                                     primary key (id)
)  ;


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE meeting_activity_seq;

CREATE TABLE IF NOT EXISTS meeting_activity (
                                                id int CHECK (id > 0) NOT NULL DEFAULT NEXTVAL ('meeting_activity_seq'),
                                                meeting_id int CHECK (meeting_id > 0) NOT NULL,
                                                activity_id int CHECK (activity_id > 0) NOT NULL,
                                                primary key (id)
)  ;

ALTER TABLE notes
    ADD CREATE INDEX notes_ibfk_1 ON notes (meeting_id),
    ADD KEY "notes_ibfk_2" (user_id);

ALTER TABLE meeting_activity
    ADD CREATE INDEX meeting_activity_ibfk_1 ON meeting_activity (meeting_id),
    ADD KEY "meeting_activity_ibfk_2" (activity_id);

ALTER TABLE agenda_points
    MODIFY COLUMN description TEXT;

ALTER TABLE attendances
    ADD CONSTRAINT attendances_ibfk_1 FOREIGN KEY (meeting_id) REFERENCES meetings (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT attendances_ibfk_2 FOREIGN KEY (user_id) REFERENCES "users" (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE notes
    ADD CONSTRAINT notes_ibfk_1 FOREIGN KEY (meeting_id) REFERENCES meetings (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT notes_ibfk_2 FOREIGN KEY (user_id) REFERENCES "users" (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE meeting_activity
    ADD CONSTRAINT meeting_activity_ibfk_1 FOREIGN KEY (meeting_id) REFERENCES meetings (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT meeting_activity_ibfk_2 FOREIGN KEY (activity_id) REFERENCES "activities" (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TYPE feedback_type AS ENUM ('none','general','specific');

ALTER TABLE meetings
    ADD COLUMN apollo_code VARCHAR(256) DEFAULT NULL,
    ADD COLUMN feedback_type feedback_type COLLATE utf8_slovak_ci NOT NULL;

ALTER TABLE agenda_points
    DROP COLUMN duration,
    ADD COLUMN "start" timestamp DEFAULT NULL,
    ADD COLUMN actual_start timestamp DEFAULT NULL,
    ADD COLUMN "end" timestamp DEFAULT NULL,
    ADD COLUMN actual_end timestamp DEFAULT NULL;

ALTER TABLE meetings
    ADD COLUMN running_activity_id cast(10 as int) UNSIGNED DEFAULT NULL,
    ADD KEY "meeting_runact_ibfk_1" (running_activity_id),
    ADD CONSTRAINT meeting_runact_ibfk_1 FOREIGN KEY (running_activity_id) REFERENCES "activities" (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ALTER COLUMN start timestamp,
    ALTER COLUMN end timestamp,
    ALTER COLUMN actual_start timestamp,
    ALTER COLUMN actual_end timestamp;

COMMIT;