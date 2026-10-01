-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 01, 2026 at 09:10 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jobfit`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `user_id`, `email`, `phone`) VALUES
(1, 3, 'admin@gmail.com', '09444444444'),
(2, 19, '', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `admin_functions`
--

CREATE TABLE `admin_functions` (
  `id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `function_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `applications`
--

CREATE TABLE `applications` (
  `id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `job_seeker_id` int(11) NOT NULL,
  `application_date` datetime NOT NULL DEFAULT current_timestamp(),
  `status` enum('Pending','Reviewed','Accepted','Rejected') NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `applications`
--

INSERT INTO `applications` (`id`, `job_id`, `job_seeker_id`, `application_date`, `status`) VALUES
(2, 4, 7, '2026-10-01 00:00:00', 'Accepted');

-- --------------------------------------------------------

--
-- Table structure for table `employer`
--

CREATE TABLE `employer` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `company_name` varchar(150) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employer`
--

INSERT INTO `employer` (`id`, `user_id`, `company_name`, `email`, `phone`) VALUES
(3, 13, 'scamcompany123', 'donutmehit@gmail.com', '09333333333'),
(4, 18, 'Alorica', 'alorica@gmail.com', '09777777777');

-- --------------------------------------------------------

--
-- Table structure for table `jobs`
--

CREATE TABLE `jobs` (
  `id` int(11) NOT NULL,
  `employer_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `title` varchar(150) NOT NULL,
  `description` text NOT NULL,
  `location` varchar(150) DEFAULT NULL,
  `salary` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jobs`
--

INSERT INTO `jobs` (`id`, `employer_id`, `category_id`, `title`, `description`, `location`, `salary`) VALUES
(4, 3, 8, 'CSR IT Whatever', 'Just handle calls and emails bruh', 'quezon city whatever', 18000.00),
(6, 3, 14, 'CSR IT something kineme', 'basta description', 'dyan sa giled', 18000.00);

-- --------------------------------------------------------

--
-- Table structure for table `job_categories`
--

CREATE TABLE `job_categories` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_categories`
--

INSERT INTO `job_categories` (`id`, `name`, `description`) VALUES
(1, 'Information Technology', 'Jobs related to information technology and computer systems'),
(3, 'Software Development', 'Jobs related to software and application development'),
(4, 'Database Administrations', 'Jobs related to database management and administration'),
(5, 'Technical Support', 'Jobs related to technical and computer support'),
(8, 'IT Suport', 'Jobs related to it support'),
(9, 'Cybersecurity', 'Protecting systems, networks, and data from cyber threats through security monitoring, vulnerability assessment, and incident prevention.'),
(10, 'laravel', 'a free, open-source PHP framework designed for building modern, scalable web applications and AI agents.'),
(12, 'Data Science', 'Jobs involving analytics and ML'),
(14, 'category', 'awdawdawdwadawdawd');

-- --------------------------------------------------------

--
-- Table structure for table `job_required_skills`
--

CREATE TABLE `job_required_skills` (
  `id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_required_skills`
--

INSERT INTO `job_required_skills` (`id`, `job_id`, `skill_id`) VALUES
(4, 4, 1),
(5, 4, 2),
(6, 4, 5),
(7, 4, 8),
(9, 6, 1),
(8, 6, 5);

-- --------------------------------------------------------

--
-- Table structure for table `job_seeker`
--

CREATE TABLE `job_seeker` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_seeker`
--

INSERT INTO `job_seeker` (`id`, `user_id`, `full_name`, `email`, `phone`) VALUES
(1, 1, 'Noel Pio N. Mamolejo', 'noel@gmail.com', '09052503181'),
(3, 8, 'wel', 'wel@gmail.com', '09121414111'),
(4, 9, 'leon', 'leon@gmail.com', '051350136'),
(5, 10, 'wel', 'jo@gmail.com', '21515`5'),
(6, 11, '65456', 'testing123@gmail.com', '09111111111'),
(7, 12, 'Nathaniel D Rodriguez Jr', 'donut@gmail.com', '09111111111'),
(8, 14, 'jobseekerbruh', 'job@gmail.com', '09555555555'),
(10, 16, 'wendel', '', NULL),
(11, 17, 'Nathaniel', 'nathan@gmail.com', '09666666666');

-- --------------------------------------------------------

--
-- Table structure for table `job_seeker_skills`
--

CREATE TABLE `job_seeker_skills` (
  `id` int(11) NOT NULL,
  `job_seeker_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `job_seeker_skills`
--

INSERT INTO `job_seeker_skills` (`id`, `job_seeker_id`, `skill_id`) VALUES
(3, 1, 2),
(2, 1, 3),
(6, 3, 2),
(5, 3, 4),
(8, 7, 1),
(14, 7, 5),
(7, 7, 7),
(9, 7, 8),
(13, 7, 10),
(12, 8, 8);

-- --------------------------------------------------------

--
-- Table structure for table `match_reports`
--

CREATE TABLE `match_reports` (
  `id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `job_seeker_id` int(11) NOT NULL,
  `match_score` decimal(5,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `match_reports`
--

INSERT INTO `match_reports` (`id`, `job_id`, `job_seeker_id`, `match_score`, `created_at`) VALUES
(2, 4, 7, 50.00, '2026-10-01 01:47:10');

-- --------------------------------------------------------

--
-- Table structure for table `skills`
--

CREATE TABLE `skills` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `skills`
--

INSERT INTO `skills` (`id`, `name`) VALUES
(4, 'HTML/CSS'),
(1, 'Java'),
(5, 'JavaScript'),
(7, 'Kotlin'),
(10, 'laravel'),
(2, 'Python'),
(3, 'SQL'),
(8, 'Vue.js');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','job_seeker','employer') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'noelpio', '$2a$10$OFsShqXEzOAknYSv9VZ4BOk5CGSfRPDEFU2JQwxEZ.G56j9t3bGaC', 'job_seeker'),
(3, 'admin', '$2a$10$kprwu85xK1mS2BI2.WTlq.8okRQx1osqU9phTxXuEi9s33gUmAq6W', 'admin'),
(8, 'wel', '$2a$10$NSfO9z3ONMCNyCPDKgVmAOwv8PQep1nns2KcB9roOTsyQe8mAEHge', 'job_seeker'),
(9, 'leon', '$2a$10$.tiYYIU2TAfkgZUwpVFb3.6EBvZ0U81W4D2aay3iapUGEbdq33pDu', 'job_seeker'),
(10, 'jo', '$2a$10$eoR0Jfp0meyzXSXkdhkU5eT6758joZx5aZQJryejM9AkTBqZu.hne', 'job_seeker'),
(11, 'Okay', '$2a$10$A9vh0t8.9bZJO/BYDBRSW.p3KbtE19RmNG4u.0B5yi61x/1.XbGX6', 'job_seeker'),
(12, 'donut', '$2a$10$8kzI7wiJB1k0fFVZGioHD.iB4RgXlh8yWrxvhq3u4as6UY6JZo7fO', 'job_seeker'),
(13, 'hitme', '$2a$10$Zjub9U1gjXOGs6r3CrqFwevnOkZDRAmpP7cbMooGdbqJR5TyPOkI2', 'employer'),
(14, 'bastauserto', '$2a$10$dzA/.24ZTTRGy4K9ekFL8.Zqb4ZdT6CxytKWqfD5Gac4zYbYP.xAa', 'job_seeker'),
(15, 'bastauserito', '$2a$10$8jzh37DagEDyUkf83Q3qleeU2vDXv5LqMfytkbmCaUr6A6HbBuBti', 'job_seeker'),
(16, 'wendel', '$2a$10$F/OC8hAG2u2QQQ1NcxITauCZaUqnzB/fjFZgrqU7SjKTc95vTwIMm', 'job_seeker'),
(17, 'Jobseeker', '$2a$10$7xiTgAA.fkNnSMEOYlPwC.Qfsar2zrWjlKNO5s/XJnwz56s92sjyy', 'job_seeker'),
(18, 'Employer', '$2a$10$ZI6FeizTADsoteDxl0rhWenmyKVgxeko5bsgBo.O6o15Rfr3pUtZq', 'employer'),
(19, 'admintest', '$2a$10$2XaDQtwmBdnPDrzVLJH2ae5303HzkukL6keUiimLBkwPyaXCwNioi', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `admin_functions`
--
ALTER TABLE `admin_functions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `applications`
--
ALTER TABLE `applications`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `job_id` (`job_id`,`job_seeker_id`),
  ADD KEY `job_seeker_id` (`job_seeker_id`);

--
-- Indexes for table `employer`
--
ALTER TABLE `employer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employer_id` (`employer_id`),
  ADD KEY `fk_jobs_category` (`category_id`);

--
-- Indexes for table `job_categories`
--
ALTER TABLE `job_categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `job_required_skills`
--
ALTER TABLE `job_required_skills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `job_id` (`job_id`,`skill_id`),
  ADD KEY `skill_id` (`skill_id`);

--
-- Indexes for table `job_seeker`
--
ALTER TABLE `job_seeker`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `job_seeker_skills`
--
ALTER TABLE `job_seeker_skills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `job_seeker_id` (`job_seeker_id`,`skill_id`),
  ADD KEY `skill_id` (`skill_id`);

--
-- Indexes for table `match_reports`
--
ALTER TABLE `match_reports`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `job_id` (`job_id`,`job_seeker_id`),
  ADD KEY `job_seeker_id` (`job_seeker_id`);

--
-- Indexes for table `skills`
--
ALTER TABLE `skills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `admin_functions`
--
ALTER TABLE `admin_functions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `applications`
--
ALTER TABLE `applications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `employer`
--
ALTER TABLE `employer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jobs`
--
ALTER TABLE `jobs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `job_categories`
--
ALTER TABLE `job_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `job_required_skills`
--
ALTER TABLE `job_required_skills`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `job_seeker`
--
ALTER TABLE `job_seeker`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `job_seeker_skills`
--
ALTER TABLE `job_seeker_skills`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `match_reports`
--
ALTER TABLE `match_reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `skills`
--
ALTER TABLE `skills`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `admin_functions`
--
ALTER TABLE `admin_functions`
  ADD CONSTRAINT `admin_functions_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `applications`
--
ALTER TABLE `applications`
  ADD CONSTRAINT `applications_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `applications_ibfk_2` FOREIGN KEY (`job_seeker_id`) REFERENCES `job_seeker` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employer`
--
ALTER TABLE `employer`
  ADD CONSTRAINT `employer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `jobs`
--
ALTER TABLE `jobs`
  ADD CONSTRAINT `fk_jobs_category` FOREIGN KEY (`category_id`) REFERENCES `job_categories` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `jobs_ibfk_1` FOREIGN KEY (`employer_id`) REFERENCES `employer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `job_required_skills`
--
ALTER TABLE `job_required_skills`
  ADD CONSTRAINT `job_required_skills_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `job_required_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `job_seeker`
--
ALTER TABLE `job_seeker`
  ADD CONSTRAINT `job_seeker_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `job_seeker_skills`
--
ALTER TABLE `job_seeker_skills`
  ADD CONSTRAINT `job_seeker_skills_ibfk_1` FOREIGN KEY (`job_seeker_id`) REFERENCES `job_seeker` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `job_seeker_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `match_reports`
--
ALTER TABLE `match_reports`
  ADD CONSTRAINT `match_reports_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `match_reports_ibfk_2` FOREIGN KEY (`job_seeker_id`) REFERENCES `job_seeker` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
