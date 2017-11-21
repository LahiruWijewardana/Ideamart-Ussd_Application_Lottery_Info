CREATE DATABASE lotteryinfo;

USE lotteryinfo;

CREATE TABLE lottery
(
  lotteryId INT AUTO_INCREMENT PRIMARY KEY,
  lotteryName VARCHAR(30) NOT NULL ,
  drawNumber INT NOT NULL ,
  numCount INT NOT NULL ,
  letterCount INT DEFAULT 0,
  bonusNumCount INT DEFAULT 0,
  hasSymbol BOOLEAN NOT NULL DEFAULT FALSE ,
  IdValue INT,
  lotterynumbers VARCHAR(20) NOT NULL,
  lotteryLetter VARCHAR(10),
  lotteryBonus VARCHAR(10),
  lotterySymbol VARCHAR(15)
);


INSERT INTO lottery
(lotteryName, drawNumber, numCount, letterCount, bonusNumCount, hasSymbol, IdValue, lotterynumbers, lotteryLetter, lotteryBonus, lotterySymbol)
VALUES
("KOTIPATHI SHANIDA", 1, 4, 1, 0, FALSE, NULL, "1 2 3 4", "a", "", ""),
("LAGNA WASANA", 1, 4, 0, 0, TRUE, NULL, "1 2 3 4", "", "", "test"),
("NIYATHA JAYA", 1, 4, 1, 0, FALSE, NULL, "1 2 3 4", "a", "", ""),
("JAYODA", 1, 4, 1, 0, FALSE, NULL, "1 2 3 4", "a", "", ""),
("DASA LAKSHAPATHI CAR PLUS", 1, 3, 1, 0, TRUE, NULL, "1 2 3", "a", "", "test"),
("GALAXY STAR", 1, 4, 0, 1, FALSE, NULL, "1 2 3 4","", "1", ""),
("DEVELOPMENT FORTUNE", 1, 4, 0, 1, TRUE, NULL, "1 2 3 4", "", "1", "test"),
("SUPER BALL", 1, 4, 1, 0, FALSE, NULL, "1 2 3 4", "a", "", ""),
("ADA KOTIPATHI", 1, 4, 1, 0, FALSE, NULL, "1 2 3 4", "a", "", ""),
("SATURDAY FORTUNE", 1, 4, 1, 1, FALSE, NULL, "1 2 3 4", "a", "1", ""),
("MAHAJANA SAMPATHA", 3537, 6, 1, 0, FALSE, 1, "1 2 3 4 5 6", "A", "", ""),
("GOVI SETHA", 1602, 4, 1, 0, FALSE, 2, "1 2 3 4", "A", "", ""),
("SUPIRI WASANA", 1274, 4, 0, 1, FALSE , 5, "1 2 3 4", "", "1", ""),
("VASANA SAMPATHA", 1460, 4, 3, 1, FALSE, 6, "1 2 3 4", "A", "1", ""),
("AIRPORT SUPER DRAW", 30, 4, 1, 0, FALSE, 40, "1 2 3 4", "A", "", ""),
("JATHIKA SAMPATHA", 788, 5, 1, 1, FALSE, 41, "1 2 3 4 5", "A", "1", ""),
("SAMPATH REKHA", 779, 4, 3, 1, FALSE, 42, "1 2 3 4", "A", "1", ""),
("DOUBLE BONUS", 3, 3, 1, 1, FALSE, 43, "1 2 3", "A", "1", ""),
("POWER LOTTO", 89, 4, 1, 1, FALSE, 45, "1 2 3 4", "A", "1", ""),
("NEEROGA", 320, 4, 0, 0, TRUE , 48, "1 2 3 4", "", "", "TEST"),
("MEGA POWER", 159, 4, 1, 1, FALSE, 49, "1 2 3 4", "A", "1", ""),
("DELAKSHAPATHI", 77, 3, 1, 0, FALSE, 50, "1 2 3", "A", "", ""),
("VASANA SUPER FIFTY", 11, 3, 0, 0, FALSE, 52, "1 2 3", "", "", ""),
("MANUSATHA MEHEWARA", 0, 3, 0 , 0, FALSE, 53, "1 2 3", "", "", ""),
("SEVANA", 33, 4, 1, 0, FALSE, 54, "1 2 3 4", "A", "", "");

SELECT * FROM lottery;

CREATE TABLE Users
(
  Id INT AUTO_INCREMENT PRIMARY KEY ,
  msisdn VARCHAR(11) NOT NULL ,
  register ENUM("0", "1", "2") NOT NULL ,
  status INT
);

INSERT INTO Users
(msisdn, register, status)
    VALUES
      ("94772933664", "0", 0),
      ("94775470513", "0", 0);

SELECT * FROM Users;
