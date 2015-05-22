CREATE TABLE `canary`.`role` (
    `Id` INT NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`Id`),
    UNIQUE INDEX `Name_UNIQUE` (`Name` ASC)
);