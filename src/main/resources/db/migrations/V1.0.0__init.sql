-- phpMyAdmin SQL Dump
-- version 5.1.1
-- SQLINES DEMO *** admin.net/
--
-- Host: 127.0.0.1
-- SQLINES DEMO *** Apr 28, 2022 at 10:49 AM
-- SQLINES DEMO *** 0.4.22-MariaDB
-- PHP Version: 8.1.1

/* SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; */
START TRANSACTION;
time_zone := "+02:00";


/* SQLINES DEMO *** ARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/* SQLINES DEMO *** ARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/* SQLINES DEMO *** LLATION_CONNECTION=@@COLLATION_CONNECTION */;
/* SQLINES DEMO *** tf8mb4 */;

--
-- SQLINES DEMO *** gapp`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `activities`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS activities (
                                          id int CHECK (id > 0) NOT NULL,
    type varchar(64) NOT NULL,
    title varchar(64) NOT NULL,
    text varchar(256) NOT NULL,
    answer varchar(256) NOT NULL,
    img_url varchar(64) DEFAULT NULL
    )  ;

--
-- SQLINES DEMO *** table `activities`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `agendas`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS agendas (
                                       id int CHECK (id > 0) NOT NULL,
    meeting_id int CHECK (meeting_id > 0) NOT NULL
    )  ;

--
-- SQLINES DEMO *** table `agendas`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `agenda_points`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS agenda_points (
                                             id int CHECK (id > 0) NOT NULL,
    number int CHECK (number > 0) NOT NULL,
    title varchar(64) NOT NULL,
    description varchar(256) NOT NULL,
    duration time(0) NOT NULL,
    agenda_id int CHECK (agenda_id > 0) NOT NULL,
    status smallint DEFAULT NULL
    )  ;

--
-- SQLINES DEMO *** table `agenda_points`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `attendees`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS attendees (
                                         id int CHECK (id > 0) NOT NULL,
    email varchar(64) NOT NULL,
    meeting_id int CHECK (meeting_id > 0) NOT NULL,
    feedback_rating int CHECK (feedback_rating > 0) NOT NULL DEFAULT 0,
    feedback_comment varchar(256) NOT NULL DEFAULT '""',
    participation enum('yes','no') NOT NULL DEFAULT 'no',
    position_id int CHECK (position_id > 0) DEFAULT NULL
    )  ;

--
-- SQLINES DEMO *** table `attendees`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `meetings`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS meetings (
                                        id int CHECK (id > 0) NOT NULL,
    exchange_id varchar(512) NOT NULL,
    subject varchar(128) NOT NULL,
    description varchar(256) DEFAULT NULL,
    organizer_id int CHECK (organizer_id > 0) NOT NULL,
    meeting_type enum('project_status','brainstorming','team_status','social','one_to_one') NOT NULL,
    start date NOT NULL,
    actual_start date DEFAULT NULL,
    end date DEFAULT NULL,
    actual_end date DEFAULT NULL,
    meeting_cost cast(11 as int) DEFAULT NULL,
    url varchar(256) COLLATE utf8_slovak_ci DEFAULT NULL,
    notes_url varchar(256) COLLATE utf8_slovak_ci NOT NULL DEFAULT '',
    location varchar(64) COLLATE utf8_slovak_ci NOT NULL DEFAULT '',
    latitude float(23) DEFAULT NULL,
    longitude float(23) DEFAULT NULL,
    activity_id cast(10 as int) UNSIGNED DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- SQLINES DEMO *** table `meetings`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `positions`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS positions (
                                         id int CHECK (id > 0) NOT NULL,
    name varchar(64) NOT NULL,
    hourly_cost int CHECK (hourly_cost > 0) NOT NULL
    )  ;

--
-- SQLINES DEMO *** table `positions`
--

-- SQLINES DEMO *** ---------------------------------------

--
-- SQLINES DEMO *** or table `users`
--

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE IF NOT EXISTS users (
                                     id int CHECK (id > 0) NOT NULL,
    name varchar(64) NOT NULL,
    email varchar(64) NOT NULL,
    modified_at timestamp(0) NOT NULL DEFAULT current_timestamp() (),
    position_id cast(10 as int) UNSIGNED DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- SQLINES DEMO *** table `users`
--

--
-- SQLINES DEMO *** d tables
--

--
-- SQLINES DEMO ***  `activities`
--
ALTER TABLE activities
    ADD PRIMARY KEY (id);

--
-- SQLINES DEMO ***  `agendas`
--
ALTER TABLE agendas
    ADD PRIMARY KEY (id),
  ADD KEY `agendas_ibfk_1` (meeting_id);

--
-- SQLINES DEMO ***  `agenda_points`
--
ALTER TABLE agenda_points
    ADD PRIMARY KEY (id),
  ADD KEY `agenda_points_ibfk_1` (agenda_id);

--
-- SQLINES DEMO ***  `attendees`
--
ALTER TABLE attendees
    ADD PRIMARY KEY (id),
  ADD KEY `position_id` (position_id),
  ADD KEY `attendees_ibfk_1` (meeting_id);

--
-- SQLINES DEMO ***  `meetings`
--
ALTER TABLE meetings
    ADD PRIMARY KEY (id),
  ADD KEY `meetings_ibfk_1` (organizer_id),
  ADD KEY `meetings_ibfk_2` (activity_id);

--
-- SQLINES DEMO ***  `positions`
--
ALTER TABLE positions
    ADD PRIMARY KEY (id);

--
-- SQLINES DEMO ***  `users`
--
ALTER TABLE users
    ADD PRIMARY KEY (id),
  ADD UNIQUE KEY `email` (email),
  ADD KEY `position_id` (position_id);

--
-- SQLINES DEMO *** r dumped tables
--

--
-- SQLINES DEMO *** r table `activities`
--
ALTER TABLE activities
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- SQLINES DEMO *** r table `agendas`
--
ALTER TABLE agendas
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- SQLINES DEMO *** r table `agenda_points`
--
ALTER TABLE agenda_points
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- SQLINES DEMO *** r table `attendees`
--
ALTER TABLE attendees
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- SQLINES DEMO *** r table `meetings`
--
ALTER TABLE meetings
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- SQLINES DEMO *** r table `positions`
--
ALTER TABLE positions
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- SQLINES DEMO *** r table `users`
--
ALTER TABLE users
    MODIFY id cast(10 as int) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- SQLINES DEMO *** umped tables
--

--
-- SQLINES DEMO *** able `agendas`
--
ALTER TABLE agendas
    ADD CONSTRAINT agendas_ibfk_1 FOREIGN KEY (meeting_id) REFERENCES meetings (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- SQLINES DEMO *** able `agenda_points`
--
ALTER TABLE agenda_points
    ADD CONSTRAINT agenda_points_ibfk_1 FOREIGN KEY (agenda_id) REFERENCES agendas (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- SQLINES DEMO *** able `attendees`
--
ALTER TABLE attendees
    ADD CONSTRAINT attendees_ibfk_1 FOREIGN KEY (meeting_id) REFERENCES meetings (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT attendees_ibfk_2 FOREIGN KEY (position_id) REFERENCES `positions` (id) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- SQLINES DEMO *** able `meetings`
--
ALTER TABLE meetings
    ADD CONSTRAINT meetings_ibfk_1 FOREIGN KEY (organizer_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT meetings_ibfk_2 FOREIGN KEY (activity_id) REFERENCES `activities` (id) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- SQLINES DEMO *** able `users`
--
ALTER TABLE users
    ADD CONSTRAINT users_ibfk_1 FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/* SQLINES DEMO *** ER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/* SQLINES DEMO *** ER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/* SQLINES DEMO *** ON_CONNECTION=@OLD_COLLATION_CONNECTION */;