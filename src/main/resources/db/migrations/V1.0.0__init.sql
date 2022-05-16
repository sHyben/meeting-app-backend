-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2022 at 10:49 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+02:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `meetingapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `activities`
--

CREATE TABLE IF NOT EXISTS `activities` (
  `id` int(10) UNSIGNED NOT NULL,
  `type` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `title` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `text` varchar(256) COLLATE utf8_slovak_ci NOT NULL,
  `answer` varchar(256) COLLATE utf8_slovak_ci NOT NULL,
  `img_url` varchar(64) COLLATE utf8_slovak_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `activities`
--

INSERT INTO `activities` (`id`, `type`, `title`, `text`, `answer`, `img_url`) VALUES
(1, 'Text', 'Vtip', 'Prečo kačica prešla cez cestu? ', 'Pretože si myslela, že je to kura.', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `agendas`
--

CREATE TABLE IF NOT EXISTS `agendas` (
  `id` int(10) UNSIGNED NOT NULL,
  `meeting_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `agendas`
--

INSERT INTO `agendas` (`id`, `meeting_id`) VALUES
(2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `agenda_points`
--

CREATE TABLE IF NOT EXISTS `agenda_points` (
  `id` int(10) UNSIGNED NOT NULL,
  `number` int(10) UNSIGNED NOT NULL,
  `title` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `description` varchar(256) COLLATE utf8_slovak_ci NOT NULL,
  `duration` time NOT NULL,
  `agenda_id` int(10) UNSIGNED NOT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `agenda_points`
--

INSERT INTO `agenda_points` (`id`, `number`, `title`, `description`, `duration`, `agenda_id`, `status`) VALUES
(2, 1, 'Agenda point 1', 'Test agenda text', '00:05:00', 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `attendees`
--

CREATE TABLE IF NOT EXISTS `attendees` (
  `id` int(10) UNSIGNED NOT NULL,
  `email` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `meeting_id` int(10) UNSIGNED NOT NULL,
  `feedback_rating` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `feedback_comment` varchar(256) COLLATE utf8_slovak_ci NOT NULL DEFAULT '""',
  `participation` enum('yes','no') COLLATE utf8_slovak_ci NOT NULL DEFAULT 'no',
  `position_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `attendees`
--

INSERT INTO `attendees` (`id`, `email`, `meeting_id`, `feedback_rating`, `feedback_comment`, `participation`, `position_id`) VALUES
(2, 'intern@gmail.com', 2, 0, '', 'no', 2);

-- --------------------------------------------------------

--
-- Table structure for table `meetings`
--

CREATE TABLE IF NOT EXISTS `meetings` (
  `id` int(10) UNSIGNED NOT NULL,
  `exchange_id` varchar(512) NOT NULL,
  `organizer_id` int(10) UNSIGNED NOT NULL,
  `meeting_type` enum('project_status','brainstorming','team_status','social','one_to_one') COLLATE utf8_slovak_ci NOT NULL,
  `start_date` date NOT NULL,
  `start_time` time NOT NULL,
  `duration` time NOT NULL,
  `actual_duration` time DEFAULT NULL,
  `meeting_cost` int(11) DEFAULT NULL,
  `url` varchar(256) COLLATE utf8_slovak_ci DEFAULT NULL,
  `notes_url` varchar(256) COLLATE utf8_slovak_ci NOT NULL DEFAULT '""',
  `activity_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `meetings`
--

INSERT INTO `meetings` (`id`, `exchange_id`, `organizer_id`, `meeting_type`, `start_date`, `start_time`, `duration`, `actual_duration`, `meeting_cost`, `url`, `notes_url`, `activity_id`) VALUES
(2, 'notSet', 25, 'project_status', '2022-04-14', '13:00:00', '01:00:00', NULL, 0, NULL, '\"\"', 1);

-- --------------------------------------------------------

--
-- Table structure for table `positions`
--

CREATE TABLE IF NOT EXISTS `positions` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `hourly_cost` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `positions`
--

INSERT INTO `positions` (`id`, `name`, `hourly_cost`) VALUES
(1, 'CEO', 500),
(2, 'Programmer', 50);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `email` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `modified_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `position_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `modified_at`, `position_id`) VALUES
(25, 'Jožko Mrkvička', 'j.mrkvicka@gmail.com', '2022-04-12 09:52:08', 1),
(28, 'Šimon Hyben', 'hyben.simon@gmail.com', '2022-04-25 22:00:00', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activities`
--
ALTER TABLE `activities`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `agendas`
--
ALTER TABLE `agendas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `agendas_ibfk_1` (`meeting_id`);

--
-- Indexes for table `agenda_points`
--
ALTER TABLE `agenda_points`
  ADD PRIMARY KEY (`id`),
  ADD KEY `agenda_points_ibfk_1` (`agenda_id`);

--
-- Indexes for table `attendees`
--
ALTER TABLE `attendees`
  ADD PRIMARY KEY (`id`),
  ADD KEY `position_id` (`position_id`),
  ADD KEY `attendees_ibfk_1` (`meeting_id`);

--
-- Indexes for table `meetings`
--
ALTER TABLE `meetings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `meetings_ibfk_1` (`organizer_id`),
  ADD KEY `meetings_ibfk_2` (`activity_id`);

--
-- Indexes for table `positions`
--
ALTER TABLE `positions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `position_id` (`position_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activities`
--
ALTER TABLE `activities`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `agendas`
--
ALTER TABLE `agendas`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `agenda_points`
--
ALTER TABLE `agenda_points`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `attendees`
--
ALTER TABLE `attendees`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `meetings`
--
ALTER TABLE `meetings`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `positions`
--
ALTER TABLE `positions`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `agendas`
--
ALTER TABLE `agendas`
  ADD CONSTRAINT `agendas_ibfk_1` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `agenda_points`
--
ALTER TABLE `agenda_points`
  ADD CONSTRAINT `agenda_points_ibfk_1` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `attendees`
--
ALTER TABLE `attendees`
  ADD CONSTRAINT `attendees_ibfk_1` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `attendees_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `meetings`
--
ALTER TABLE `meetings`
  ADD CONSTRAINT `meetings_ibfk_1` FOREIGN KEY (`organizer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `meetings_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
