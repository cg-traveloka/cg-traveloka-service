CREATE DATABASE IF NOT EXISTS `cg-traveloka-service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cg-traveloka-service`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: cg-traveloka-service
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `air_plant_brand`
--

DROP TABLE IF EXISTS `air_plant_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `air_plant_brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logo_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `partner_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjb71u0cakxl8rvg24yjuo6kpk` (`partner_id`),
  CONSTRAINT `FKjb71u0cakxl8rvg24yjuo6kpk` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `air_plant_brand`
--

LOCK TABLES `air_plant_brand` WRITE;
/*!40000 ALTER TABLE `air_plant_brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `air_plant_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `air_port_location`
--

DROP TABLE IF EXISTS `air_port_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `air_port_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrrjmjl5nbnphnrvs5elb6uo3k` (`city_id`),
  CONSTRAINT `FKrrjmjl5nbnphnrvs5elb6uo3k` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `air_port_location`
--

LOCK TABLES `air_port_location` WRITE;
/*!40000 ALTER TABLE `air_port_location` DISABLE KEYS */;
INSERT INTO `air_port_location` VALUES (1,'Sân bay quốc tế Tân Sơn Nhất (SGN)',30),(2,'Sân bay quốc tế Nội Bài (HAN)',24),(3,'Sân bay quốc tế Đà Nẵng (DAD)',15),(4,'Sân bay quốc tế Vân Đồn, Hạ Long (VDO)',49),(5,'Sân bay quốc tế Phú Quốc (PQC)',33),(7,'Sân bay Quốc tế Cát Bi (HPH)',27),(8,'Sân bay Quốc tế Phú Bài (HUI)',57),(9,'Sân bay Quốc tế Cam Ranh (CXR)',32),(10,'Sân bay Quốc tế Liên Khương (DLI)',38),(11,'Sân bay Quốc tế Phù Cát (UIH)',9),(12,'Sân bay Quốc tế Cần Thơ (VCA)',14),(13,'Sân bay Điện Biên Phủ (DIN)',18),(14,'Sân bay Thọ Xuân (THD)',56),(15,'Sân bay Đồng Hới (VDH)',46),(16,'Sân bay Chu Lai (VCL)',48),(17,'Sân bay Tuy Hòa (TBB)',45),(18,'Sân bay Pleiku (PXU)',21),(19,'Sân bay Buôn Mê Thuột (BMV)',16),(20,'Sân bay Rạch Giá (VKG)',33),(21,'Sân bay Cà Mau (CAH)',12),(22,'Sân bay Côn Đảo (VCS)',2);
/*!40000 ALTER TABLE `air_port_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bed_type`
--

DROP TABLE IF EXISTS `bed_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bed_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bed_type`
--

LOCK TABLES `bed_type` WRITE;
/*!40000 ALTER TABLE `bed_type` DISABLE KEYS */;
INSERT INTO `bed_type` VALUES (1,'Single'),(2,'Double'),(3,'Twin'),(4,'Queen'),(5,'King');
/*!40000 ALTER TABLE `bed_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Tỉnh An Giang'),(2,'Tỉnh Bà Rịa - Vũng Tàu'),(3,'Tỉnh Bạc Liêu'),(4,'Tỉnh Bắc Giang'),(5,'Tỉnh Bắc Kạn'),(6,'Tỉnh Bắc Ninh'),(7,'Tỉnh Bến Tre'),(8,'Tỉnh Bình Dương'),(9,'Tỉnh Bình Định'),(10,'Tỉnh Bình Phước'),(11,'Tỉnh Bình Thuận'),(12,'Tỉnh Cà Mau'),(13,'Tỉnh Cao Bằng'),(14,'Thành phố Cần Thơ'),(15,'Thành phố Đà Nẵng'),(16,'Tỉnh Đắk Lắk'),(17,'Tỉnh Đắk Nông'),(18,'Tỉnh Điện Biên'),(19,'Tỉnh Đồng Nai'),(20,'Tỉnh Đồng Tháp'),(21,'Tỉnh Gia Lai'),(22,'Tỉnh Hà Giang'),(23,'Tỉnh Hà Nam'),(24,'Thành phố Hà Nội'),(25,'Tỉnh Hà Tĩnh'),(26,'Tỉnh Hải Dương'),(27,'Thành phố Hải Phòng'),(28,'Tỉnh Hậu Giang'),(29,'Tỉnh Hoà Bình'),(30,'Thành phố Hồ Chí Minh'),(31,'Tỉnh Hưng Yên'),(32,'Tỉnh Khánh Hòa'),(33,'Tỉnh Kiên Giang'),(34,'Tỉnh Kon Tum'),(35,'Tỉnh Lai Châu'),(36,'Tỉnh Lạng Sơn'),(37,'Tỉnh Lào Cai'),(38,'Tỉnh Lâm Đồng'),(39,'Tỉnh Long An'),(40,'Tỉnh Nam Định'),(41,'Tỉnh Nghệ An'),(42,'Tỉnh Ninh Bình'),(43,'Tỉnh Ninh Thuận'),(44,'Tỉnh Phú Thọ'),(45,'Tỉnh Phú Yên'),(46,'Tỉnh Quảng Bình'),(47,'Tỉnh Quảng Nam'),(48,'Tỉnh Quảng Ngãi'),(49,'Tỉnh Quảng Ninh'),(50,'Tỉnh Quảng Trị'),(51,'Tỉnh Sóc Trăng'),(52,'Tỉnh Sơn La'),(53,'Tỉnh Tây Ninh'),(54,'Tỉnh Thái Bình'),(55,'Tỉnh Thái Nguyên'),(56,'Tỉnh Thanh Hóa'),(57,'Tỉnh Thừa Thiên Huế'),(58,'Tỉnh Tiền Giang'),(59,'Tỉnh Trà Vinh'),(60,'Tỉnh Tuyên Quang'),(61,'Tỉnh Vĩnh Long'),(62,'Tỉnh Vĩnh Phúc'),(63,'Tỉnh Yên Bái');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combo`
--

DROP TABLE IF EXISTS `combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_money` int DEFAULT NULL,
  `room_contract_id` int DEFAULT NULL,
  `ticket_air_plant_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6uq8qipojvael6cge5hnfv295` (`room_contract_id`),
  UNIQUE KEY `UK_bbu2ebjmo91oi79ebcjyr30te` (`ticket_air_plant_id`),
  CONSTRAINT `FKgpfsyv0jyjv93xagppn47iq31` FOREIGN KEY (`ticket_air_plant_id`) REFERENCES `ticket_air_plant` (`id`),
  CONSTRAINT `FKnx41bculqtl6a7mol791u63fv` FOREIGN KEY (`room_contract_id`) REFERENCES `room_contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo`
--

LOCK TABLES `combo` WRITE;
/*!40000 ALTER TABLE `combo` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hho535192qm8r8xchi17sqlfn` (`user_email`),
  CONSTRAINT `FK33ty8iwpkni4gv1mpq8vdpo5u` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight_information`
--

DROP TABLE IF EXISTS `flight_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `air_plant_brand_id` int DEFAULT NULL,
  `from_airport_location_id` int DEFAULT NULL,
  `to_airport_location_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcm8pbqqdy9x7rh2myc2lubx0w` (`air_plant_brand_id`),
  KEY `FK66igyxade6ahecpthexjh5bxa` (`from_airport_location_id`),
  KEY `FKi97dl83vpl8n3b0ecb41ecbwy` (`to_airport_location_id`),
  CONSTRAINT `FK66igyxade6ahecpthexjh5bxa` FOREIGN KEY (`from_airport_location_id`) REFERENCES `air_port_location` (`id`),
  CONSTRAINT `FKcm8pbqqdy9x7rh2myc2lubx0w` FOREIGN KEY (`air_plant_brand_id`) REFERENCES `air_plant_brand` (`id`),
  CONSTRAINT `FKi97dl83vpl8n3b0ecb41ecbwy` FOREIGN KEY (`to_airport_location_id`) REFERENCES `air_port_location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight_information`
--

LOCK TABLES `flight_information` WRITE;
/*!40000 ALTER TABLE `flight_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `flight_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hotel_booked_numbers` int DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `hotel_star` double NOT NULL,
  `city_id` int DEFAULT NULL,
  `partner_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf1iabdv6bi2yohh9h48wce42x` (`city_id`),
  KEY `FKdd651in0b4td05jlgffo904bs` (`partner_id`),
  CONSTRAINT `FKdd651in0b4td05jlgffo904bs` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`),
  CONSTRAINT `FKf1iabdv6bi2yohh9h48wce42x` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_hotel_utility`
--

DROP TABLE IF EXISTS `hotel_hotel_utility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_hotel_utility` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int DEFAULT NULL,
  `hotel_utility_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpjvep4hb4c4ycxt029sluci2q` (`hotel_id`),
  KEY `FKcghqypnwvbkj87tgfwhs1j86x` (`hotel_utility_id`),
  CONSTRAINT `FKcghqypnwvbkj87tgfwhs1j86x` FOREIGN KEY (`hotel_utility_id`) REFERENCES `hotel_utility` (`id`),
  CONSTRAINT `FKpjvep4hb4c4ycxt029sluci2q` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_hotel_utility`
--

LOCK TABLES `hotel_hotel_utility` WRITE;
/*!40000 ALTER TABLE `hotel_hotel_utility` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel_hotel_utility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_img`
--

DROP TABLE IF EXISTS `hotel_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_img` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `hotel_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdbgdxmbk6egxalo596cbvs35g` (`hotel_id`),
  CONSTRAINT `FKdbgdxmbk6egxalo596cbvs35g` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_img`
--

LOCK TABLES `hotel_img` WRITE;
/*!40000 ALTER TABLE `hotel_img` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel_img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_review`
--

DROP TABLE IF EXISTS `hotel_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `rating_point` int DEFAULT NULL,
  `hotel_id` int DEFAULT NULL,
  `contract_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK93xannw8r60drn1edtfa5e1e9` (`hotel_id`),
  KEY `FKnitbxqkdc4dnoscwdc3tmd59r` (`contract_id`),
  CONSTRAINT `FK93xannw8r60drn1edtfa5e1e9` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`),
  CONSTRAINT `FKnitbxqkdc4dnoscwdc3tmd59r` FOREIGN KEY (`contract_id`) REFERENCES `room_contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_review`
--

LOCK TABLES `hotel_review` WRITE;
/*!40000 ALTER TABLE `hotel_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_utility`
--

DROP TABLE IF EXISTS `hotel_utility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_utility` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `hotel_utility_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhp9nrfm1nj2l8ppa3q2it633e` (`hotel_utility_type_id`),
  CONSTRAINT `FKhp9nrfm1nj2l8ppa3q2it633e` FOREIGN KEY (`hotel_utility_type_id`) REFERENCES `hotel_utility_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_utility`
--

LOCK TABLES `hotel_utility` WRITE;
/*!40000 ALTER TABLE `hotel_utility` DISABLE KEYS */;
INSERT INTO `hotel_utility` VALUES (1,'Truyền hình cáp',1),(2,'Bàn làm việc',1),(3,'Máy sấy tóc',1),(4,'Két an toàn trong phòng',1),(5,'Minibar',1),(6,'Phòng tắm vòi sen',1),(7,'TV',1),(8,'Dịch vụ thu đổi ngoại tệ',2),(9,'Lễ tân 24h',2),(10,'Bảo vệ 24 giờ',2),(11,'Dịch vụ trả phòng muộn',2),(12,'Dịch vụ giặt ủi',2),(13,'Dịch vụ hỗ trợ đặt Tour',2),(14,'Máy lạnh',3),(15,'Phòng gia đình',3),(16,'Phòng không hút thuốc',3),(17,'Sân thượng/sân hiên',3),(18,'Dịch vụ giữ trẻ',4),(19,'Dịch vụ giữ thú cưng',4),(20,'Máy ATM/Ngân hàng',5),(21,'Thẩm mỹ viện',5),(22,'Cửa hàng thực phẩm',5),(23,'Hiệu làm tóc',5),(24,'Giặt ủi',5),(25,'Cửa hàng',5),(26,'Siêu thị',5),(27,'Bữa sáng với thực đơn gọi món',6),(28,'Bữa sáng',6),(29,'Bữa sáng món tự chọn',6),(30,'Bữa sáng (thu phí)',6),(31,'Tiệc liên hoan',6),(32,'Dịch vụ văn phòng',7),(33,'Các tiện nghi văn phòng',7),(34,'Máy photocopy',7),(35,'Trung tâm dịch vụ hành chánh/văn phòng',7),(36,'Wifi (miễn phí)',8),(37,'Cà phê/trà tại sảnh',9),(38,'Thang máy',9),(39,'Nhà hàng',9),(40,'Dịch vụ dọn phòng',9),(41,'Két an toàn',9),(42,'WiFi tại khu vực chung',9),(43,'Đưa đón trong khu vực (thu phí)',10),(44,'Dịch vụ cho thuê xe đạp',10),(45,'Giữ xe đạp',10),(46,'Cho thuê xe hơi',10),(47,'Đưa đón sân bay (thu phí)',11),(48,'Đón khách tại ga tàu',11);
/*!40000 ALTER TABLE `hotel_utility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_utility_type`
--

DROP TABLE IF EXISTS `hotel_utility_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_utility_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_utility_type`
--

LOCK TABLES `hotel_utility_type` WRITE;
/*!40000 ALTER TABLE `hotel_utility_type` DISABLE KEYS */;
INSERT INTO `hotel_utility_type` VALUES (1,'Tiện nghi phòng'),(2,'Dịch vụ khách sạn'),(3,'Tiện nghi chung'),(4,'Trẻ em và Thú cưng'),(5,'Các tiện ích lân cận'),(6,'Ẩm thực'),(7,'Tiện nghi văn phòng'),(8,'Kết nối mạng'),(9,'Tiện nghi công cộng'),(10,'Vận chuyển'),(11,'Đưa đón');
/*!40000 ALTER TABLE `hotel_utility_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partner`
--

DROP TABLE IF EXISTS `partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partner` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7t2b4h1qx3gx7eb5auk3ko08r` (`user_email`),
  CONSTRAINT `FKg6sdvmvx3vq6yc3p90gqw3axc` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partner`
--

LOCK TABLES `partner` WRITE;
/*!40000 ALTER TABLE `partner` DISABLE KEYS */;
/*!40000 ALTER TABLE `partner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_CUSTOMER'),(2,'ROLE_PARTNER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `max_person` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `size` double DEFAULT NULL,
  `unit_price_origin` int DEFAULT NULL,
  `unit_price_sell` int DEFAULT NULL,
  `bed_type_id` int DEFAULT NULL,
  `hotel_id` int DEFAULT NULL,
  `room_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKru1ie35g0w6qsg3y90lmypipo` (`bed_type_id`),
  KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`),
  KEY `FKd468eq7j1cbue8mk20qfrj5et` (`room_type_id`),
  CONSTRAINT `FKd468eq7j1cbue8mk20qfrj5et` FOREIGN KEY (`room_type_id`) REFERENCES `room_type` (`id`),
  CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`),
  CONSTRAINT `FKru1ie35g0w6qsg3y90lmypipo` FOREIGN KEY (`bed_type_id`) REFERENCES `bed_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_contract`
--

DROP TABLE IF EXISTS `room_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_contract` (
  `id` int NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `room_quantity` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `total_money` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `room_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5o2bwvku7tjwldy6eguwmul9a` (`customer_id`),
  KEY `FK9yaly2o31ocdv21ce8mft1jmi` (`room_id`),
  CONSTRAINT `FK5o2bwvku7tjwldy6eguwmul9a` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK9yaly2o31ocdv21ce8mft1jmi` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_contract`
--

LOCK TABLES `room_contract` WRITE;
/*!40000 ALTER TABLE `room_contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_room_utility`
--

DROP TABLE IF EXISTS `room_room_utility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_room_utility` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int DEFAULT NULL,
  `room_utility_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yqwahva0087fljc0kfs16j85` (`room_id`),
  KEY `FKnm5qn7hk1g3ofqimf6p9ny9xc` (`room_utility_id`),
  CONSTRAINT `FK5yqwahva0087fljc0kfs16j85` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `FKnm5qn7hk1g3ofqimf6p9ny9xc` FOREIGN KEY (`room_utility_id`) REFERENCES `room_utility` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_room_utility`
--

LOCK TABLES `room_room_utility` WRITE;
/*!40000 ALTER TABLE `room_room_utility` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_room_utility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_type`
--

LOCK TABLES `room_type` WRITE;
/*!40000 ALTER TABLE `room_type` DISABLE KEYS */;
INSERT INTO `room_type` VALUES (2,'Standard'),(3,'Deluxe'),(4,'Superior'),(5,'Suit');
/*!40000 ALTER TABLE `room_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_utility`
--

DROP TABLE IF EXISTS `room_utility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_utility` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `room_utility_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKose0ls4eo8r3m41dfsptqefm0` (`room_utility_type_id`),
  CONSTRAINT `FKose0ls4eo8r3m41dfsptqefm0` FOREIGN KEY (`room_utility_type_id`) REFERENCES `room_utility_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_utility`
--

LOCK TABLES `room_utility` WRITE;
/*!40000 ALTER TABLE `room_utility` DISABLE KEYS */;
INSERT INTO `room_utility` VALUES (1,'Máy lạnh',1),(2,'Quầy bar mini',1),(3,'Bàn làm việc',1),(4,'Rèm cửa / màn che',1),(5,'Nước đóng chai miễn phí',1),(6,'TV',1),(7,'Két an toàn tại phòng',1),(8,'Phòng tắm riêng',2),(9,'Bộ vệ sinh cá nhân',2),(10,'Áo choàng tắm',2),(11,'Máy sấy tóc',2),(12,'Vòi tắm đứng',2),(13,'Bồn tắm',2);
/*!40000 ALTER TABLE `room_utility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_utility_type`
--

DROP TABLE IF EXISTS `room_utility_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_utility_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_utility_type`
--

LOCK TABLES `room_utility_type` WRITE;
/*!40000 ALTER TABLE `room_utility_type` DISABLE KEYS */;
INSERT INTO `room_utility_type` VALUES (1,'Tiện nghi phòng'),(2,'Tiện nghi phòng tắm');
/*!40000 ALTER TABLE `room_utility_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_information`
--

DROP TABLE IF EXISTS `seat_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `unit_price` int DEFAULT NULL,
  `flight_id` int DEFAULT NULL,
  `seat_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb13q12ahun1jn88n9wle5xdr8` (`flight_id`),
  KEY `FKph57je9a52d98upl20fiorr3e` (`seat_type_id`),
  CONSTRAINT `FKb13q12ahun1jn88n9wle5xdr8` FOREIGN KEY (`flight_id`) REFERENCES `flight_information` (`id`),
  CONSTRAINT `FKph57je9a52d98upl20fiorr3e` FOREIGN KEY (`seat_type_id`) REFERENCES `seat_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_information`
--

LOCK TABLES `seat_information` WRITE;
/*!40000 ALTER TABLE `seat_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_type`
--

DROP TABLE IF EXISTS `seat_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_type`
--

LOCK TABLES `seat_type` WRITE;
/*!40000 ALTER TABLE `seat_type` DISABLE KEYS */;
INSERT INTO `seat_type` VALUES (1,'phổ thông'),(2,'phổ thông đặc biệt'),(3,'thương gia'),(4,'hạng nhất');
/*!40000 ALTER TABLE `seat_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_air_plant`
--

DROP TABLE IF EXISTS `ticket_air_plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_air_plant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `total_money` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `flight_id` int DEFAULT NULL,
  `set_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbnlx2mq1js4k6cqbd99h3c17p` (`customer_id`),
  KEY `FK67iny7viejx6jcmxtnad4bxt1` (`flight_id`),
  KEY `FKh1mwh1silh9p7eff1jpybx8vr` (`set_type_id`),
  CONSTRAINT `FK67iny7viejx6jcmxtnad4bxt1` FOREIGN KEY (`flight_id`) REFERENCES `flight_information` (`id`),
  CONSTRAINT `FKbnlx2mq1js4k6cqbd99h3c17p` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FKh1mwh1silh9p7eff1jpybx8vr` FOREIGN KEY (`set_type_id`) REFERENCES `seat_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_air_plant`
--

LOCK TABLES `ticket_air_plant` WRITE;
/*!40000 ALTER TABLE `ticket_air_plant` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_air_plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `email` varchar(255) NOT NULL,
  `enable` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FKdc28wohvgju313d3pisccud2` (`user_email`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKdc28wohvgju313d3pisccud2` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-07 17:26:43
