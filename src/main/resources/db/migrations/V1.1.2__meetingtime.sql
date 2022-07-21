/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
time_zone := "+02:00";

ALTER TABLE meetings
    ADD COLUMN anticipated_end_time datetime DEFAULT NULL;

ALTER TABLE agenda_points
    ADD COLUMN anticipated_start_time datetime DEFAULT NULL,
    ADD COLUMN anticipated_end_time datetime DEFAULT NULL;

COMMIT;