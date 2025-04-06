CREATE DATABASE  IF NOT EXISTS `anki_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `anki_db`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: anki_db
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vocabulary`
--

DROP TABLE IF EXISTS `vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vocabulary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `word` varchar(255) NOT NULL,
  `meaning` varchar(255) NOT NULL,
  `learned` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `word_UNIQUE` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vocabulary`
--

LOCK TABLES `vocabulary` WRITE;
/*!40000 ALTER TABLE `vocabulary` DISABLE KEYS */;
INSERT INTO `vocabulary` VALUES (12,'Koncert','コンサート',1),(14,'Gdje je～?','～はどこですか？',0),(16,'Da','はい',1),(17,'Ne','いいえ',1),(18,'Laku　noć','おやすみなさい',1),(19,'Gdje ćemo jesti?','どこで食べますか？',0),(20,'Drago mi je','はじめまして',0),(21,'Zovem se～','私の名前は～です',0),(22,'Ja sam～','私は～です',0),(23,'Kako se zovete?','あなたのお名前は？',0),(24,'Hvala','ありがとう',0),(25,'Izvolite','どうぞ',0),(26,'Oprostite','ごめんなさい',0),(27,'gdje','どこ',0),(28,'zašto','なぜ',0),(29,'kako','いつ',0),(30,'Ne znam','知りませ',0),(31,'jedan','1',1),(32,'dva','2',1),(33,'tri','3 ',1),(34,'četiri','4',0),(35,'pet','5',0),(36,'šest','6',0),(37,'sedam','7',0),(38,'osam','8',0),(39,'devet','9',0),(40,'deset','10',0),(43,'bok','やあ',0),(44,'molim','お願いします',0),(45,'koliko?','いくつですか？',0),(46,'Koliko ovo košta?','これはいくらですか？',0),(49,'majmun','猿',0),(50,'riba','魚',0),(51,'lav','ライオン',1),(52,'mačka','猫',0),(53,'ptica','鳥',0),(55,'A ti?','君は？',0),(56,'Koliko je sati?','何時ですか？',0),(57,'Jutro','朝',0),(59,'Zagreb','ザグレブ',0),(60,'Vidimo se','また会いましょう',0),(61,'Sretan rođendan','誕生日おめでとう',0),(62,'Ćao','やぁ',0),(64,'Hrvatska','クロアチア',0),(66,'dobar','良い',0),(70,'Sretan Božić','メリークリスマス',0),(85,'Pas','犬',0);
/*!40000 ALTER TABLE `vocabulary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-06 15:21:42
