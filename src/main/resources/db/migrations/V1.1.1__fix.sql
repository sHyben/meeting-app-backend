/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
SET timezone TO "+02:00";

CREATE TYPE status_type AS ENUM ('PENDING','ONGOING', 'DONE', 'SKIPPED');

ALTER TABLE agenda_points
    ALTER COLUMN "status" DROP DEFAULT,
    ALTER COLUMN "status" TYPE status_type USING
        CASE "status"
            WHEN 0 THEN 'PENDING'
            WHEN 0 THEN 'ONGOING'
            WHEN 0 THEN 'DONE'
            WHEN 0 THEN 'SKIPPED'
        END :: status_type,
    ALTER COLUMN "status" SET DEFAULT 'PENDING';

CREATE CAST (character varying AS feedback_type) WITH INOUT AS IMPLICIT;
CREATE CAST (character varying AS meeting_type) WITH INOUT AS IMPLICIT;
CREATE CAST (character varying AS status_type) WITH INOUT AS IMPLICIT;


COMMIT;