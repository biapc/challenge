USE `desafio`;


DROP TABLE IF EXISTS `cav`;
CREATE TABLE `cav` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `open_hours` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
)


DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(30) NOT NULL,
  `model` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
)


DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day` date NOT NULL,
  `hour` int NOT NULL,
  `cav_id` int NOT NULL,
  `car_id` int NOT NULL,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cav_id` (`cav_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `calendar_ibfk_1` FOREIGN KEY (`cav_id`) REFERENCES `cav` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `calendar_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE RESTRICT
  UNIQUE (day, hour, cav_id, type)
)


