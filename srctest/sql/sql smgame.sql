DROP DATABASE IF EXISTS smgame;
CREATE DATABASE smgame;

DROP TABLE IF EXISTS `smgame`.`transactions`;

CREATE TABLE  `smgame`.`transactions` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `game_id` bigint(20) unsigned NOT NULL,
  `manche` int(10) unsigned NOT NULL,
  `player` varchar(25) NOT NULL,
  `score` double NOT NULL,
  `winlose` double NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_2` (`game_id`,`manche`,`player`)
);

DROP TABLE IF EXISTS `smgame`.`cards`;

CREATE TABLE  `smgame`.`cards` (
  `id` smallint(5) unsigned NOT NULL,
  `suit` varchar(7) NOT NULL,
  `point` varchar(7) NOT NULL,
  PRIMARY KEY  (`id`)
);

DROP TABLE IF EXISTS `smgame`.`transaction_card`;

CREATE TABLE  `smgame`.`transaction_card` (
  `transaction_ID` bigint(20) unsigned NOT NULL,
  `card_id` smallint(5) unsigned NOT NULL,
  KEY `FK_TRANSACTIONS_CARD_1` (`transaction_ID`),
  KEY `FK_TRANSACTIONS_CARD_2` (`card_id`),
  CONSTRAINT `FK_TRANSACTIONS_CARD_1` FOREIGN KEY (`transaction_ID`) REFERENCES `transactions` (`id`),
  CONSTRAINT `FK_TRANSACTIONS_CARD_2` FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`)
);

INSERT INTO `smgame`.`cards` VALUES(1, 'Danari', 'Asso');
INSERT INTO `smgame`.`cards` VALUES(2, 'Danari', 'Due');
INSERT INTO `smgame`.`cards` VALUES(3, 'Danari', 'Tre');
INSERT INTO `smgame`.`cards` VALUES(4, 'Danari', 'Quattro');
INSERT INTO `smgame`.`cards` VALUES(5, 'Danari', 'Cinque');
INSERT INTO `smgame`.`cards` VALUES(6, 'Danari', 'Sei');
INSERT INTO `smgame`.`cards` VALUES(7, 'Danari', 'Sette');
INSERT INTO `smgame`.`cards` VALUES(8, 'Danari', 'Donna');
INSERT INTO `smgame`.`cards` VALUES(9, 'Danari', 'Fante');
INSERT INTO `smgame`.`cards` VALUES(10, 'Danari', 'Re');
INSERT INTO `smgame`.`cards` VALUES(11, 'Coppe', 'Asso');
INSERT INTO `smgame`.`cards` VALUES(12, 'Coppe', 'Due');
INSERT INTO `smgame`.`cards` VALUES(13, 'Coppe', 'Tre');
INSERT INTO `smgame`.`cards` VALUES(14, 'Coppe', 'Quattro');
INSERT INTO `smgame`.`cards` VALUES(15, 'Coppe', 'Cinque');
INSERT INTO `smgame`.`cards` VALUES(16, 'Coppe', 'Sei');
INSERT INTO `smgame`.`cards` VALUES(17, 'Coppe', 'Sette');
INSERT INTO `smgame`.`cards` VALUES(18, 'Coppe', 'Donna');
INSERT INTO `smgame`.`cards` VALUES(19, 'Coppe', 'Fante');
INSERT INTO `smgame`.`cards` VALUES(20, 'Coppe', 'Re');
INSERT INTO `smgame`.`cards` VALUES(21, 'Spade', 'Asso');
INSERT INTO `smgame`.`cards` VALUES(22, 'Spade', 'Due');
INSERT INTO `smgame`.`cards` VALUES(23, 'Spade', 'Tre');
INSERT INTO `smgame`.`cards` VALUES(24, 'Spade', 'Quattro');
INSERT INTO `smgame`.`cards` VALUES(25, 'Spade', 'Cinque');
INSERT INTO `smgame`.`cards` VALUES(26, 'Spade', 'Sei');
INSERT INTO `smgame`.`cards` VALUES(27, 'Spade', 'Sette');
INSERT INTO `smgame`.`cards` VALUES(28, 'Spade', 'Donna');
INSERT INTO `smgame`.`cards` VALUES(29, 'Spade', 'Fante');
INSERT INTO `smgame`.`cards` VALUES(30, 'Spade', 'Re');
INSERT INTO `smgame`.`cards` VALUES(31, 'Bastoni', 'Asso');
INSERT INTO `smgame`.`cards` VALUES(32, 'Bastoni', 'Due');
INSERT INTO `smgame`.`cards` VALUES(33, 'Bastoni', 'Tre');
INSERT INTO `smgame`.`cards` VALUES(34, 'Bastoni', 'Quattro');
INSERT INTO `smgame`.`cards` VALUES(35, 'Bastoni', 'Cinque');
INSERT INTO `smgame`.`cards` VALUES(36, 'Bastoni', 'Sei');
INSERT INTO `smgame`.`cards` VALUES(37, 'Bastoni', 'Sette');
INSERT INTO `smgame`.`cards` VALUES(38, 'Bastoni', 'Donna');
INSERT INTO `smgame`.`cards` VALUES(39, 'Bastoni', 'Fante');
INSERT INTO `smgame`.`cards` VALUES(40, 'Bastoni', 'Re');