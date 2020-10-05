DROP DATABASE IF EXISTS `desafio`;
CREATE DATABASE `desafio`;
USE `desafio`;


DROP TABLE IF EXISTS `cav`;
CREATE TABLE `cav` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `open_hours` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(30) NOT NULL,
  `model` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
);


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
  CONSTRAINT `calendar_ibfk_1` FOREIGN KEY (`cav_id`) REFERENCES `cav` (`id`) ON DELETE CASCADE,
  CONSTRAINT `calendar_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE CASCADE,
  UNIQUE (day, hour, cav_id, type)
);


INSERT INTO `cav` (`id`, `name`, `open_hours`) VALUES
(1,	'Botafogo',	'{\"segunda\":{\"inicio\":10, \"fim\":17}, \"terca\":{\"inicio\":10, \"fim\":17}, \"quarta\":{\"inicio\":10, \"fim\":17}, \"quinta\":{\"inicio\":10, \"fim\":17}, \"sexta\":{\"inicio\":10, \"fim\":17}, \"sabado\":{\"inicio\":10, \"fim\":15}, \"domingo\":{} }'),
(2,	'Barra da Tijuca',	'{\"segunda\":{\"inicio\":10, \"fim\":17}, \"terca\":{\"inicio\":10, \"fim\":17}, \"quarta\":{\"inicio\":10, \"fim\":17}, \"quinta\":{\"inicio\":10, \"fim\":17}, \"sexta\":{\"inicio\":10, \"fim\":17}, \"sabado\":{\"inicio\":10, \"fim\":15}, \"domingo\":{} }'),
(3,	'Norte Shopping',	'{\"segunda\":{\"inicio\":10, \"fim\":17}, \"terca\":{\"inicio\":10, \"fim\":17}, \"quarta\":{\"inicio\":10, \"fim\":17}, \"quinta\":{\"inicio\":10, \"fim\":17}, \"sexta\":{\"inicio\":10, \"fim\":17}, \"sabado\":{\"inicio\":10, \"fim\":15}, \"domingo\":{} }');


INSERT INTO `car` (`id`, `brand`, `model`) VALUES
(1,	'VW',	'Golf'),
(2,	'Ford',	'Fiesta'),
(3,	'GM',	'Cruze'),
(4,	'GM',	'Cobalt'),
(5,	'GM',	'Cobalt'),
(6,	'Fiat',	'Palio'),
(7,	'GVW',	'Up');


INSERT INTO `calendar` (`id`, `day`, `hour`, `cav_id`, `car_id`, `type`) VALUES
(1,	'2019-07-17',	11,	1,	1,	'visit'),
(2,	'2019-07-17',	14,	1,	7,	'visit'),
(3,	'2019-07-17',	11,	1,	7,	'inspection'),
(4,	'2019-07-17',	11,	3,	2,	'inspection'),
(5,	'2019-07-17',	11,	2,	3,	'visit'),
(6,	'2019-07-17',	10,	2,	3,	'inspection'),
(7,	'2019-07-17',	11,	2,	4,	'inspection'),
(8,	'2019-07-17',	12,	2,	5,	'inspection'),
(9,	'2019-07-18',	10,	3,	2,	'visit'),
(10,	'2019-07-18',	14,	3,	2,	'visit');


