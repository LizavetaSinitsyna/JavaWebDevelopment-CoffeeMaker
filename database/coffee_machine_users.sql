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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `role_id` bigint NOT NULL DEFAULT '2',
  `bonus_account_id` bigint NOT NULL,
  `account_id` bigint NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(13) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `bonus_account_id_UNIQUE` (`bonus_account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  KEY `fk_user_bonus_account1_idx` (`bonus_account_id`),
  KEY `fk_user_account1_idx` (`account_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `account_id` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `bonus_account_id` FOREIGN KEY (`bonus_account_id`) REFERENCES `bonus_accounts` (`bonus_account_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'lizaveta@gmail.com','$2a$10$.xXpxkOxS/Go5Uoxy7jcPe9w7UvFl7JtUFrYLvOX9zwpdA9z76yAG',1,1,1,'Lizaveta','lizaveta@gmail.com','+375293287201');
INSERT INTO `users` VALUES (2,'vitaliy@gmail.com','$2a$10$2Hx5TMcqsUF2MeWDkkad0uoFj06GUW7goiIkPbUD0QjKacVqC.u7a',2,2,2,'Vitaliy','vitaliy@gmail.com','+375257865174');
INSERT INTO `users` VALUES (3,'vasya@gmail.com','$2a$10$2Hx5TMcqsUF2MeWDkkad0uoFj06GUW7goiIkPbUD0QjKacVqC.u7a',2,3,3,'Vasya','vasya@gmail.com','+375291112233');
INSERT INTO `users` VALUES (4,'Svetlana','$2a$10$UM6kku5Tedz3TOvjm8/tJu8pSvFoJ/gwYs8L015iQ6n1Re6XNLkX2',2,4,4,'Sveta','sveta@gmail.com','+375295586923');
INSERT INTO `users` VALUES (5,'Char19','$2a$10$6m9iNmr2SRx6Llb/QOQEh.Vpux2/GawGb5z.bbU8o9ahwtWw92sbS',2,5,5,'Чарли','charles@gmail.com','+375251112233');
INSERT INTO `users` VALUES (6,'renat','$2a$10$jofGkPod.ccb6nrK4mbZJO0iTH6p5ExXf3EU1I8P1k3KkyZ7mFVx6',2,6,6,'renata','renata@yl.jp','+375291111111');
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

-- Dump completed on 2021-11-01 23:38:42
