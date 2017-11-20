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
  lotterynumbers VARCHAR(20) NOT NULL,
  lotteryLetter VARCHAR(5),
  lotteryBonus VARCHAR(5),
  lotterySymbol VARCHAR(15)
);


INSERT INTO lottery
(lotteryName, drawNumber, numCount, letterCount, bonusNumCount, hasSymbol,lotterynumbers, lotteryLetter, lotteryBonus, lotterySymbol)
VALUES
("KOTIPATHI SHANIDA", 1, 4, 1, 0, FALSE, "1 2 3 4", "a", "", ""),
("LAGNA WASANA", 1, 4, 0, 0, TRUE, "1 2 3 4", "", "", "test"),
("NIYATHA JAYA", 1, 4, 1, 0, FALSE, "1 2 3 4", "a", "", ""),
("JAYODA", 1, 4, 1, 0, FALSE, "1 2 3 4", "a", "", ""),
("DASA LAKSHAPATHI CAR PLUS", 1, 3, 1, 0, TRUE, "1 2 3", "a", "", "test"),
("GALAXY STAR", 1, 4, 0, 1, FALSE, "1 2 3 4","", "1", ""),
("DEVELOPMENT FORTUNE", 1, 4, 0, 1, TRUE,"1 2 3 4", "", "1", "test"),
("SUPER BALL", 1, 4, 1, 0, FALSE, "1 2 3 4", "a", "", ""),
("ADA KOTIPATHI", 1, 4, 1, 0, FALSE, "1 2 3 4", "a", "", ""),
("SATURDAY FORTUNE", 1, 4, 1, 1, FALSE, "1 2 3 4", "a", "1", "");

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
