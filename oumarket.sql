-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: oumarket
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `adress` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `adress_UNIQUE` (`adress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('304a012e-fdd4-44b2-b791-36c3ff5526d5','OUMarket Nguyễn Kiệm','371 Nguyễn Kiệm'),('6258d87e-22c4-401a-a9cc-53553dd6fb49','Branch 001','123 Main St.'),('71c471a5-ce70-45bc-8b48-e3fbf5e78070','OUMarket Âu Cơ','254 Âu Cơ'),('8e5d832e-d8b7-4827-bab7-20bafd0ce0e5','OUMarket Lạc Long Quân','281 Lạc Long Quân'),('dd82dabd-991f-4b6b-9169-17df15e45dbc','Branch 2','456 Second St.'),('e2ebd96a-5c18-4777-804e-bf39eeef8a5c','Branch 002','654 Main St.');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `categoryname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `categoryname_UNIQUE` (`categoryname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('18','Bánh'),('19','Chăm Sóc Cơ Thể'),('20','Chăm Sóc Sức Khỏe'),('1','Dầu Ăn'),('3','Đồ Dùng Gia Đình'),('4','Gạo, Nếp, Đậu, Bột'),('2','Gia Vị Nêm'),('5','Giặt Xả'),('6','Giấy Vệ Sinh, Khăn Giấy'),('7','Rau Củ'),('8','Sữa Các Loại'),('9','Thịt'),('14','Thủy Hải Sản'),('10','Thực Phẩm Ăn Liền'),('11','Thực Phẩm Trữ Đông'),('12','Thức Uống Có Cồn'),('13','Thức Uống Không Cồn'),('15','Trái Cây'),('16','Trứng'),('17','Vệ Sinh Nhà Cửa');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `dateofbirth` datetime NOT NULL,
  `sex` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `phonenumber` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `phonenumber_UNIQUE` (`phonenumber`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('1','Lê Đạt','2023-04-04 00:00:00','Nam','01232458743','dat2@gmail.com'),('2','Hoàng','2002-03-02 00:00:00','Nam','01232356423','hoang@gmail.com'),('3','Dũ','2023-04-19 00:00:00','Nam','01234534122','du@gmail.com'),('4','Hiếu','2002-04-20 00:00:00','Nam','01234335443','hieu@gmail.com');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `price` float NOT NULL,
  `origin` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `categoryID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_category_product_idx` (`categoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('aa0649d8-a32e-45eb-b059-4f3a84484352','Bột Giặt Omo','Bịch',7000,'Việt Nam','5'),('b1b8c7f2-39e3-4660-8d2d-f1f94fa4269d','Sữa Tươi Vinamilk','Hộp',10000,'Việt Nam','8'),('d4a9da6b-5010-4e26-8e36-96177aa896bb','Đường Cát Hạ Long','Kg',23000,'Việt Nam','2'),('e275c05c-0351-4f23-a804-ef40005dcaca','Dầu Ăn Tường An','Chai',25000,'Việt Nam','1');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `fromdate` date NOT NULL,
  `todate` date NOT NULL,
  `newprice` float NOT NULL,
  `productID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_promotion_product_idx` (`productID`),
  CONSTRAINT `fk_promotion_product` FOREIGN KEY (`productID`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES ('036827bd-c647-422e-a040-2bbb243ee8f1','2023-04-03','2023-04-20',3000,'d4a9da6b-5010-4e26-8e36-96177aa896bb'),('250a8ab6-b3a8-4f56-9e66-115f5f4ae1dc','2023-04-20','2023-04-22',10000,'e275c05c-0351-4f23-a804-ef40005dcaca'),('c0da98a7-eddf-4c43-8cc6-08d9586ac2ae','2023-04-11','2023-04-27',3000,'aa0649d8-a32e-45eb-b059-4f3a84484352'),('ca39272d-c99c-4f52-8ed0-8fa50b775651','2023-04-05','2023-04-20',1000,'b1b8c7f2-39e3-4660-8d2d-f1f94fa4269d');
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `createddate` datetime NOT NULL,
  `temptotal` float DEFAULT NULL,
  `promotiontotal` float DEFAULT NULL,
  `birthday` float DEFAULT '0',
  `total` float NOT NULL,
  `staffID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `customerID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_receipt_employee_idx` (`staffID`),
  KEY `fk_receipt_customer_idx` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES ('c32759c1-b7a6-430a-9b06-519beca37eee','2023-04-19 00:00:00',1290000,31000,0.1,1133100,'64acd540-7c61-4dad-9e8f-6efba1343652','3'),('e5245606-5595-40cb-af33-722b9ef33739','2023-04-16 00:00:00',30000,20000,0,10000,'64acd540-7c61-4dad-9e8f-6efba1343652',NULL);
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_detail`
--

DROP TABLE IF EXISTS `receipt_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_detail` (
  `quantity` float NOT NULL,
  `productID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `receiptID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`productID`,`receiptID`),
  KEY `fk_receiptdetail_detail_idx` (`receiptID`),
  KEY `fk_receiptdetail_product_idx` (`productID`),
  CONSTRAINT `fk_receiptdetail_detail` FOREIGN KEY (`receiptID`) REFERENCES `receipt` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_receiptdetail_product` FOREIGN KEY (`productID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_detail`
--

LOCK TABLES `receipt_detail` WRITE;
/*!40000 ALTER TABLE `receipt_detail` DISABLE KEYS */;
INSERT INTO `receipt_detail` VALUES (1,'aa0649d8-a32e-45eb-b059-4f3a84484352','c32759c1-b7a6-430a-9b06-519beca37eee'),(1,'aa0649d8-a32e-45eb-b059-4f3a84484352','e5245606-5595-40cb-af33-722b9ef33739'),(1,'b1b8c7f2-39e3-4660-8d2d-f1f94fa4269d','c32759c1-b7a6-430a-9b06-519beca37eee'),(1,'d4a9da6b-5010-4e26-8e36-96177aa896bb','c32759c1-b7a6-430a-9b06-519beca37eee'),(1,'d4a9da6b-5010-4e26-8e36-96177aa896bb','e5245606-5595-40cb-af33-722b9ef33739'),(50,'e275c05c-0351-4f23-a804-ef40005dcaca','c32759c1-b7a6-430a-9b06-519beca37eee');
/*!40000 ALTER TABLE `receipt_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `dateofbirth` datetime DEFAULT NULL,
  `sex` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `phonenumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `role` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `branchID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `phonenumber_UNIQUE` (`phonenumber`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_branch_idx` (`branchID`),
  CONSTRAINT `fk_user_branch` FOREIGN KEY (`branchID`) REFERENCES `branch` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('2','Phạm Tấn Hoàng',NULL,NULL,'',NULL,'Admin','hoang','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','',NULL),('64acd540-7c61-4dad-9e8f-6efba1343652','Lê Quang Đạt','371 Nguyễn Kiệm','2023-04-06 00:00:00','Nam','0923564875','staff','dat1','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','304a012e-fdd4-44b2-b791-36c3ff5526d5','dat@gmail.com'),('98dffcc7-40f0-4057-be31-6d1f06654f78','Nguyễn Văn A','123 ABC St.','2002-12-06 00:00:00','Nam','0909123456','staff','nguyenvana','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','71c471a5-ce70-45bc-8b48-e3fbf5e78070','nguyenvana@gmail.com'),('e8e64e90-a200-4651-806d-afeb41c41f13','Nguyễn Văn Bảo','456 XYZ St.','1970-01-01 00:00:00','Nam','0987654321','staff','nguyenvanb','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','71c471a5-ce70-45bc-8b48-e3fbf5e78070','nguyenvanb@gmail.com'),('ff3c6de1-2396-4b7f-98c1-20e49f04a1e9','Nguyễn Văn D','123 ABC St.','1970-01-01 00:00:00','Nam','0909123423','staff','nguyenvand','pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=','71c471a5-ce70-45bc-8b48-e3fbf5e78070','nguyenvand@gmail.com');
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

-- Dump completed on 2023-04-19 19:18:56
