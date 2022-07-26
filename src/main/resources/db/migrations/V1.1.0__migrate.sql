START TRANSACTION;
SET timezone TO "+02:00";

DROP TABLE IF EXISTS "attendees";

CREATE TABLE IF NOT EXISTS "attendances" (
    "id" SERIAL PRIMARY KEY,
    "feedback_rating" INTEGER DEFAULT NULL,
    "feedback_comment" varchar(256) DEFAULT NULL,
    "participation" BOOLEAN NOT NULL DEFAULT '0',
    "meeting_id" INTEGER NOT NULL,
    "user_id" INTEGER NOT NULL,
    "last_joined_at" timestamp DEFAULT NULL,
    "presence_time" INTEGER DEFAULT '0',
    CONSTRAINT attendances_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id),
    CONSTRAINT attendances_ibfk_2
        FOREIGN KEY(user_id)
            REFERENCES users(id)
    );

--
CREATE TABLE IF NOT EXISTS "notes" (
    "id" SERIAL PRIMARY KEY,
    "meeting_id" INTEGER NOT NULL,
    "user_id" INTEGER NOT NULL,
    "text" TEXT NOT NULL,
    CONSTRAINT notes_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id),
    CONSTRAINT notes_ibfk_2
        FOREIGN KEY(user_id)
            REFERENCES users(id)
    );


CREATE TABLE IF NOT EXISTS "meeting_activity" (
    "id" SERIAL PRIMARY KEY,
    "meeting_id" INTEGER NOT NULL,
    "activity_id" INTEGER NOT NULL,
    CONSTRAINT meeting_activity_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id),
    CONSTRAINT meeting_activity_ibfk_2
        FOREIGN KEY(activity_id)
            REFERENCES activities(id)
    );

ALTER TABLE "agenda_points"
    ALTER COLUMN "description" TYPE TEXT;

CREATE TYPE feedback_type AS ENUM ('none','general','specific');

ALTER TABLE "meetings"
    ADD COLUMN "apollo_code" VARCHAR(256) DEFAULT NULL,
    ADD COLUMN "feedback_type" feedback_type NOT NULL;

ALTER TABLE "agenda_points"
    DROP COLUMN "duration",
    ADD COLUMN "start" timestamp DEFAULT NULL,
    ADD COLUMN "actual_start" timestamp DEFAULT NULL,
    ADD COLUMN "end" timestamp DEFAULT NULL,
    ADD COLUMN "actual_end" timestamp DEFAULT NULL;

ALTER TABLE "meetings"
    ADD COLUMN "running_activity_id" INTEGER DEFAULT NULL,
    ADD CONSTRAINT "meeting_runact_ibfk_1" FOREIGN KEY ("running_activity_id") REFERENCES "activities" ("id"),
    ALTER COLUMN "start" TYPE timestamp,
    ALTER COLUMN "end" TYPE timestamp,
    ALTER COLUMN "actual_start" TYPE timestamp,
    ALTER COLUMN "actual_end" TYPE timestamp;

COMMIT;