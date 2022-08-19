/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
SET timezone TO "+02:00";

ALTER TABLE meetings
    ADD COLUMN feedback_url varchar(256) DEFAULT NULL;

ALTER TABLE notes
    ADD COLUMN created_at timestamp DEFAULT NULL;

COMMIT;