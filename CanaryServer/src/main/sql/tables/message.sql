CREATE TABLE `canary`.`message` (
    `Id` INT NOT NULL AUTO_INCREMENT,
    `AuthorUser_Id` INT NOT NULL,
    `Value` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`Id`),
    INDEX `FK_Message_User_idx` (`AuthorUser_Id` ASC),
    CONSTRAINT `FK_Message_User`
        FOREIGN KEY (`AuthorUser_Id`)
        REFERENCES `canary`.`user` (`Id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);