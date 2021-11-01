-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: coffee_machine
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `order_drinks`
--

DROP TABLE IF EXISTS `order_drinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_drinks` (
  `order_id` bigint NOT NULL,
  `drink_id` bigint NOT NULL,
  `drink_count` int DEFAULT NULL,
  PRIMARY KEY (`order_id`,`drink_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `drink_id_idx` (`drink_id`),
  CONSTRAINT `drink_id` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_drinks`
--

LOCK TABLES `order_drinks` WRITE;
/*!40000 ALTER TABLE `order_drinks` DISABLE KEYS */;
INSERT INTO `order_drinks` VALUES (1,1,2);
INSERT INTO `order_drinks` VALUES (2,1,1);
INSERT INTO `order_drinks` VALUES (5,3,4);
INSERT INTO `order_drinks` VALUES (5,4,1);
INSERT INTO `order_drinks` VALUES (11,5,1);
INSERT INTO `order_drinks` VALUES (12,5,1);
INSERT INTO `order_drinks` VALUES (13,5,2);
INSERT INTO `order_drinks` VALUES (16,2,1);
INSERT INTO `order_drinks` VALUES (16,3,1);
INSERT INTO `order_drinks` VALUES (16,5,1);
/*!40000 ALTER TABLE `order_drinks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-01 23:38:41
