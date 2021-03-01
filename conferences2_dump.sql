CREATE DATABASE  IF NOT EXISTS `conferences2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `conferences2`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: conferences2
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `location` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` VALUES (1,'2021-01-07 23:53:00','Kyiv','Java Core'),(2,'2021-02-17 23:54:00','Київ','Джава Кор'),(3,'2021-02-07 21:09:00','Харків','Сервлети'),(4,'2021-03-07 21:09:00','Kharkiv','Servlets'),(5,'2021-02-07 21:09:00','Lviv','JSP'),(6,'2021-02-07 21:09:00','Dnipro','HTTP'),(7,'2021-02-07 21:09:00','Вінниця','Колекції'),(8,'2021-03-07 21:09:00','Черкаси','Святкова Джава'),(9,'2021-05-07 00:00:00','Одеса','Джава і 2 дельфіни'),(10,'2020-12-07 21:09:00','Рівне','DAO and Tao'),(11,'2020-11-07 21:09:00','Poltava','Philip Dick and Programming'),(12,'2021-02-07 21:09:00','Mykolaiv','The Wheel'),(13,'2021-03-02 00:00:00','asdf','asdf'),(14,'2021-03-02 00:00:00','asdf','asdf'),(17,'2021-03-03 00:00:00','asdf','asdf'),(18,'2021-03-02 00:00:00',' rber','sdnrn'),(20,'2021-02-23 00:00:00','sdfas','attempt');
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_participants`
--

DROP TABLE IF EXISTS `conference_participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conference_participants` (
  `conference_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`conference_id`,`user_id`),
  KEY `FKgcsupn74ev1op28qdoc4w9cet` (`user_id`),
  CONSTRAINT `FKayimbckubvkq3pq5sbvkwgf3q` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKgcsupn74ev1op28qdoc4w9cet` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_participants`
--

LOCK TABLES `conference_participants` WRITE;
/*!40000 ALTER TABLE `conference_participants` DISABLE KEYS */;
INSERT INTO `conference_participants` VALUES (9,15);
/*!40000 ALTER TABLE `conference_participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presentation`
--

DROP TABLE IF EXISTS `presentation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `presentation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conference_id` bigint(20) NOT NULL,
  `speaker_id` bigint(20) DEFAULT NULL,
  `time` time NOT NULL,
  `topic` varchar(255) NOT NULL,
  `speaker_approved` tinyint(1) DEFAULT NULL,
  `presentation_approved` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkvr8gbsx74xi88lirydxjle05` (`conference_id`),
  KEY `FKrkk7ulh88vttx7p4wixqem8l4` (`speaker_id`),
  CONSTRAINT `FKkvr8gbsx74xi88lirydxjle05` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKrkk7ulh88vttx7p4wixqem8l4` FOREIGN KEY (`speaker_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentation`
--

LOCK TABLES `presentation` WRITE;
/*!40000 ALTER TABLE `presentation` DISABLE KEYS */;
INSERT INTO `presentation` VALUES (1,1,14,'04:25:00','Інтерфейси',0,1),(2,1,14,'01:25:00','Класи',1,1),(3,1,14,'02:28:00','Big Time Ride',1,1),(4,1,14,'03:34:00','PKD and Relition',1,1),(5,1,14,'01:42:00','Tao and Three Stigmata',0,0),(6,1,14,'01:43:00','Sci-Fi and Cold War',0,0),(7,1,14,'01:45:00','UBIK',1,1),(8,3,14,'12:09:00','Bladerunner Androids',1,1),(9,3,14,'14:10:00','Why JSP',1,1),(10,3,14,'21:13:00','Why servlets are dying out',0,1),(11,3,14,'21:13:00','Drop the Base',1,0),(12,3,14,'17:19:00','Less Serve',0,0);
/*!40000 ALTER TABLE `presentation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (13,'moderator@gmail.com','asdf','sadf','$2a$10$LI2wSGrN1XHVD0N6a4h1vep9CF99chqpGFpugpSBlk0t80g/WOHEW','MODERATOR'),(14,'speaker@gmail.com','sdbdfb','sfxvu','$2a$10$LI2wSGrN1XHVD0N6a4h1vep9CF99chqpGFpugpSBlk0t80g/WOHEW','SPEAKER'),(15,'participant@gmail.com','xcvdf','vcsdf','$2a$10$LI2wSGrN1XHVD0N6a4h1vep9CF99chqpGFpugpSBlk0t80g/WOHEW','PARTICIPANT'),(16,'speaker2@gmail.com','S','S','$2a$10$nkghpazHnymcrRzHxmUAweFrOYT/JjXhuKy2X7P8EEbcekeLTzXh.','SPEAKER'),(17,'speaker3@gmail.com','S','T','$2a$10$vUdTLQkBZE9DYVgwp.gAGub7xeP8V7CsStT7GR8M1sMfvOHw/xXGW','SPEAKER'),(18,'speaker4@gmail.com','S','F','$2a$10$aCYsJWM0Y9SWtTNVsZNzMuRYOWylr4/tgvr4AmB4A96wmFXGuy.sy','SPEAKER'),(19,'speaker5@gmail.com','S','F','$2a$10$Iqmg3Nt5DG21SdMYPIJPtOiQ7nt7enyioJXdOKS4NEloTdj5D4EQC','SPEAKER'),(20,'speaker6@gmail.com','S','Sixth','$2a$10$oQeY0jbm7REm.GIP3oUvFuoZVtF3cD00TrQiGUGR1yVmQSGnAGr1S','SPEAKER'),(21,'speaker7@gmail.com','Speaker','Sev','$2a$10$jL9hRxPG0GTQivbOFL.yVeCBgvZZnXbpOLPxOgiBP4y..wRwZn.Em','SPEAKER'),(22,'speaker8@gmail.com','Speaker','Eighth','$2a$10$CdwuNrP3fBkshjRZZrfpgOeV7voqqxAcIq3niFtWjNZfKhAEyBROG','SPEAKER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-01 13:18:55
