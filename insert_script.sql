USE `desafio`;


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
(1,	'2019-07-17',	11,	1,	1,	'visita'),
(2,	'2019-07-17',	14,	1,	7,	'visita'),
(3,	'2019-07-17',	11,	1,	7,	'inspecao'),
(4,	'2019-07-17',	11,	3,	2,	'inspecao'),
(5,	'2019-07-17',	11,	2,	3,	'visita'),
(6,	'2019-07-17',	10,	2,	3,	'inspecao'),
(7,	'2019-07-17',	11,	2,	4,	'inspecao'),
(8,	'2019-07-17',	12,	2,	5,	'inspecao'),
(9,	'2019-07-18',	10,	3,	2,	'visita'),
(10,	'2019-07-18',	14,	3,	2,	'visita');


