-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jun 21, 2022 at 11:48 AM
-- Server version: 8.0.29
-- PHP Version: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `meetingapp-db`
--

-- --------------------------------------------------------

--
-- Table structure for table `activities`
--

CREATE TABLE IF NOT EXISTS  `activities` (
  `id` int UNSIGNED NOT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `text` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `answer` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `img_url` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

-- --------------------------------------------------------

--
-- Table structure for table `agendas`
--

CREATE TABLE IF NOT EXISTS `agendas` (
  `id` int UNSIGNED NOT NULL,
  `meeting_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

-- --------------------------------------------------------

--
-- Table structure for table `agenda_points`
--

CREATE TABLE IF NOT EXISTS `agenda_points` (
  `id` int UNSIGNED NOT NULL,
  `number` int UNSIGNED NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `description` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `duration` time NOT NULL,
  `agenda_id` int UNSIGNED NOT NULL,
  `status` tinyint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

-- --------------------------------------------------------

--
-- Table structure for table `attendances`
--

CREATE TABLE IF NOT EXISTS `attendances` (
  `id` int UNSIGNED NOT NULL,
  `feedback_rating` int DEFAULT NULL,
  `feedback_comment` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `participation` tinyint(1) NOT NULL DEFAULT '0',
  `meeting_id` int UNSIGNED NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  `presence_time` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `flyway_schema_history`
--

CREATE TABLE IF NOT EXISTS `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `meetings`
--

CREATE TABLE IF NOT EXISTS `meetings` (
  `id` int UNSIGNED NOT NULL,
  `exchange_id` varchar(512) COLLATE utf8_slovak_ci NOT NULL,
  `subject` varchar(128) COLLATE utf8_slovak_ci NOT NULL,
  `description` varchar(256) COLLATE utf8_slovak_ci DEFAULT NULL,
  `organizer_id` int UNSIGNED NOT NULL,
  `meeting_type` enum('project_status','brainstorming','team_status','social','one_to_one') CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `start` date NOT NULL,
  `actual_start` date DEFAULT NULL,
  `end` date DEFAULT NULL,
  `actual_end` date DEFAULT NULL,
  `meeting_cost` int DEFAULT NULL,
  `url` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci DEFAULT NULL,
  `notes_url` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL DEFAULT '',
  `location` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL DEFAULT '',
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `activity_id` int UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

-- --------------------------------------------------------

--
-- Table structure for table `positions`
--

CREATE TABLE IF NOT EXISTS `positions` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `hourly_cost` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `email` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_slovak_ci NOT NULL,
  `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `position_id` int UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

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
-- Indexes for table `attendances`
--
ALTER TABLE `attendances`
  ADD PRIMARY KEY (`id`),
  ADD KEY `meeting_id` (`meeting_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

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
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `agendas`
--
ALTER TABLE `agendas`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `agenda_points`
--
ALTER TABLE `agenda_points`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `attendances`
--
ALTER TABLE `attendances`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `meetings`
--
ALTER TABLE `meetings`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `positions`
--
ALTER TABLE `positions`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

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
-- Constraints for table `attendances`
--
ALTER TABLE `attendances`
  ADD CONSTRAINT `attendances_ibfk_1` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `attendances_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

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
