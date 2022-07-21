/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
SET timezone TO "+02:00";

CREATE TYPE status_type AS ENUM ('PENDING','ONGOING', 'DONE', 'SKIPPED');

ALTER TABLE agenda_points
    ALTER COLUMN "status" status_type NOT NULL DEFAULT 'PENDING';


COMMIT;