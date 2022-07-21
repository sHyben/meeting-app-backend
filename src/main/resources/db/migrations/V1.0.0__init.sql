START TRANSACTION;
SET timezone TO "+02:00";

CREATE TABLE IF NOT EXISTS activities (
    id SERIAL PRIMARY KEY,
    "type" varchar(64) NOT NULL,
    title varchar(64) NOT NULL,
    text varchar(256) NOT NULL,
    answer varchar(256) NOT NULL,
    img_url varchar(64) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS positions (
    id SERIAL PRIMARY KEY,
    "name" varchar(64) NOT NULL,
    hourly_cost int CHECK (hourly_cost > 0) NOT NULL
);

CREATE TYPE participation AS ENUM ('yes', 'no');

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    "name" varchar(64) NOT NULL,
    email varchar(64) NOT NULL UNIQUE,
    modified_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    position_id INTEGER DEFAULT NULL,
    CONSTRAINT users_ibfk_1
        FOREIGN KEY(position_id)
            REFERENCES positions(id)
);

CREATE TYPE meeting_type AS ENUM ('project_status','brainstorming','team_status','social','one_to_one');

CREATE TABLE IF NOT EXISTS meetings (
    id SERIAL PRIMARY KEY,
    exchange_id varchar(512) NOT NULL,
    subject varchar(128) NOT NULL,
    description varchar(256) DEFAULT NULL,
    organizer_id int CHECK (organizer_id > 0) NOT NULL,
    meeting_type meeting_type NOT NULL,
    "start" timestamp NOT NULL,
    actual_start timestamp DEFAULT NULL,
    "end" timestamp DEFAULT NULL,
    actual_end timestamp DEFAULT NULL,
    meeting_cost INTEGER DEFAULT NULL,
    url varchar(256) DEFAULT NULL,
    notes_url varchar(256) NOT NULL DEFAULT '',
    location varchar(64) NOT NULL DEFAULT '',
    latitude float(23) DEFAULT NULL,
    longitude float(23) DEFAULT NULL,
    activity_id INTEGER DEFAULT NULL,
    CONSTRAINT meetings_ibfk_1
        FOREIGN KEY(organizer_id)
            REFERENCES users(id),
    CONSTRAINT meetings_ibfk_2
        FOREIGN KEY(activity_id)
            REFERENCES activities(id)
);

CREATE TABLE IF NOT EXISTS agendas (
    id SERIAL PRIMARY KEY,
    meeting_id int CHECK (meeting_id > 0) NOT NULL,
    CONSTRAINT agendas_ibfk_1
    FOREIGN KEY(meeting_id)
    REFERENCES meetings(id)
);

CREATE TABLE IF NOT EXISTS attendees (
    id SERIAL PRIMARY KEY,
    email varchar(64) NOT NULL,
    meeting_id int CHECK (meeting_id > 0) NOT NULL,
    feedback_rating int CHECK (feedback_rating > 0) NOT NULL DEFAULT 0,
    feedback_comment varchar(256) NOT NULL DEFAULT '""',
    participation participation NOT NULL DEFAULT 'no',
    position_id int CHECK (position_id > 0) DEFAULT NULL,
    CONSTRAINT attendees_ibfk_1
        FOREIGN KEY(meeting_id)
            REFERENCES meetings(id),
    CONSTRAINT attendees_ibfk_2
        FOREIGN KEY(position_id)
            REFERENCES positions(id)
);

CREATE TABLE IF NOT EXISTS agenda_points (
    id SERIAL PRIMARY KEY,
    "number" int CHECK (number > 0) NOT NULL,
    title varchar(64) NOT NULL,
    description varchar(256) NOT NULL,
    duration time(0) NOT NULL,
    agenda_id int CHECK (agenda_id > 0) NOT NULL,
    status smallint DEFAULT NULL,
    CONSTRAINT agenda_points_ibfk_1
        FOREIGN KEY(agenda_id)
            REFERENCES agendas(id)
);
