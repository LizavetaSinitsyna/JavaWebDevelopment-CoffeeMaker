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
-- Table structure for table `drink_ingredients`
--

DROP TABLE IF EXISTS `drink_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drink_ingredients` (
  `drink_id` bigint NOT NULL,
  `ingredient_id` bigint NOT NULL,
  `ingredient_amount` int NOT NULL,
  `is_optional` tinyint NOT NULL,
  PRIMARY KEY (`drink_id`,`ingredient_id`),
  KEY `drink_id_idx` (`drink_id`),
  KEY `ingredient_id_idx` (`ingredient_id`),
  CONSTRAINT `drink_ingredients_drink_id` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`),
  CONSTRAINT `ingredient_id` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drink_ingredients`
--

LOCK TABLES `drink_ingredients` WRITE;
/*!40000 ALTER TABLE `drink_ingredients` DISABLE KEYS */;
INSERT INTO `drink_ingredients` VALUES (1,1,40,0);
INSERT INTO `drink_ingredients` VALUES (1,2,150,0);
INSERT INTO `drink_ingredients` VALUES (1,6,50,0);
INSERT INTO `drink_ingredients` VALUES (2,1,20,0);
INSERT INTO `drink_ingredients` VALUES (2,6,150,0);
INSERT INTO `drink_ingredients` VALUES (3,1,25,0);
INSERT INTO `drink_ingredients` VALUES (3,2,150,0);
INSERT INTO `drink_ingredients` VALUES (3,6,40,0);
INSERT INTO `drink_ingredients` VALUES (4,1,25,0);
INSERT INTO `drink_ingredients` VALUES (4,6,100,0);
INSERT INTO `drink_ingredients` VALUES (5,1,40,1);
INSERT INTO `drink_ingredients` VALUES (5,2,50,0);
INSERT INTO `drink_ingredients` VALUES (5,6,100,0);
/*!40000 ALTER TABLE `drink_ingredients` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-01 23:38:42
