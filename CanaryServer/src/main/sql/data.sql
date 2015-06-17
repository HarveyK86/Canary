-- canary
INSERT INTO `canary`.`message` (`Value`)
VALUES ('Hello, World!');

-- permission
SET SQL_MODE=(SELECT CONCAT(@@SQL_MODE, ',NO_AUTO_VALUE_ON_ZERO'));

INSERT INTO `canary`.`permission` (`Id`, `Name`)
VALUES (0, 'CREATE_MESSAGE'),
	   (1, 'READ_MESSAGE'),
	   (2, 'UPDATE_MESSAGE'),
	   (3, 'DELETE_MESSAGE'),
	   (4, 'CREATE_USER'),
	   (5, 'READ_USER'),
	   (6, 'UPDATE_USER'),
	   (7, 'DELETE_USER');

SET SQL_MODE=(SELECT REPLACE(@@SQL_MODE, 'NO_AUTO_VALUE_ON_ZERO', ''));