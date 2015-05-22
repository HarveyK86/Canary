CREATE TABLE `canary`.`user` (
    `Id` INT NOT NULL AUTO_INCREMENT,
    `Username` VARCHAR(45) BINARY NOT NULL,
    `Password` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`Id`),
    UNIQUE INDEX `Username_UNIQUE` (`Username` ASC)
);