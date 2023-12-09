-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `categoryID` int NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(45) NOT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Drinks'),(5,'Hamburger');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishItem`
--

DROP TABLE IF EXISTS `dishItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishItem` (
  `dishID` int unsigned NOT NULL AUTO_INCREMENT,
  `dishName` varchar(45) NOT NULL,
  `unitPrice` double NOT NULL,
  `imageURI` varchar(200) NOT NULL,
  `categoryID` int NOT NULL,
  PRIMARY KEY (`dishID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishItem`
--

LOCK TABLES `dishItem` WRITE;
/*!40000 ALTER TABLE `dishItem` DISABLE KEYS */;
INSERT INTO `dishItem` VALUES (1,'Milk',3,'file:/Users/deboo/Downloads/milk.jpeg',1),(2,'dddd',11,'images/defaultimage.jpeg',2),(3,'ooo',10,'/images/defaultimage.jpeg',2),(4,'BeefBurger',30,'/images/defaultimage.jpeg',3),(5,'Beef Burger',30,'images/defaultimage.jpeg',4),(6,'Beef Burger',30,'images/defaultimage.jpeg',5);
/*!40000 ALTER TABLE `dishItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `status` int NOT NULL,
  `time` datetime NOT NULL,
  `dishes` varchar(200) NOT NULL,
  `total_cost` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (8,'6d645586-c8a5-4375-b4d5-6519ddf0cf0f','4',2,'2023-12-03 17:56:05','Milk*1',3),(9,'a5adc9df-e54e-4581-be54-d617fc187094','4',2,'2023-12-05 10:59:48','Milk*1',3),(10,'552c102c-a285-425e-ae67-1e4fbb8bfbf4','4',2,'2023-12-05 13:23:08','Milk*1',3),(11,'eccbacf5-21c3-43b2-9916-24dd24ffb90c','8',2,'2023-12-05 14:55:32','Milk*5',15),(12,'b851b8a1-2af0-4ec0-bbb9-71b681fefcce','8',1,'2023-12-05 14:56:58','Milk*11',33),(13,'05c59c61-3f03-400c-9ef9-a636f1408837','8',2,'2023-12-05 15:00:11','Milk*3',9),(14,'a1021039-55af-4450-b04a-9d583770ff5a','8',2,'2023-12-05 15:04:39','Milk*3',9),(15,'f92bf6d5-101c-4e3e-8452-db49e9f385f2','8',2,'2023-12-05 15:05:01','Milk*6',18),(16,'719fa798-adef-468d-92f7-e732efd0e74e','4',1,'2023-12-05 15:35:35','Milk*3',9),(17,'93ff5dc3-3729-4a9b-9755-17d7ea469865','4',1,'2023-12-05 15:41:04','BeefBurger*1,Milk*2',36),(18,'16ce2705-1e0b-495b-845b-76f3619d7914','4',2,'2023-12-05 15:41:29','Milk*1',3),(19,'db3cdf5f-9f93-431a-80ae-af81aa496543','11',1,'2023-12-06 17:29:14','Beef Burger*2,Milk*1',63),(20,'7b191ab6-d1ab-4c96-b383-c2c80a45e520','12',1,'2023-12-06 18:03:06','Beef Burger*2,Milk*1',63);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `id_UNIQUE` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','aaa'),(2,'newuser','12345'),(3,'user1','11111'),(4,'notauser','000'),(5,'testuser','12345'),(6,'user2','1122'),(7,'addnew','111'),(8,'wangba','111'),(9,'dogdog','aaa'),(10,'new','nnn'),(11,'customer','12345'),(12,'customer1','11222'),(13,'customer1','1122');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-09 12:21:23
