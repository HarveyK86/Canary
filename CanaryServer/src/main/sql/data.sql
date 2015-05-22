-- canary
INSERT INTO `canary`.`canary` (`Message`)
VALUES ('Hello, World!');

-- role
SET SQL_MODE=(SELECT CONCAT(@@SQL_MODE, ',NO_AUTO_VALUE_ON_ZERO'));

INSERT INTO `canary`.`role` (`Id`, `Name`)
VALUES (0, 'USER');

SET SQL_MODE=(SELECT REPLACE(@@SQL_MODE, 'NO_AUTO_VALUE_ON_ZERO', ''));