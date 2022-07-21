/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
SET timezone TO "+02:00";

ALTER TABLE meetings
    ADD COLUMN anticipated_end_time timestamp DEFAULT NULL;

ALTER TABLE agenda_points
    ADD COLUMN anticipated_start_time timestamp DEFAULT NULL,
    ADD COLUMN anticipated_end_time timestamp DEFAULT NULL;

COMMIT;