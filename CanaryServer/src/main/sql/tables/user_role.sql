CREATE TABLE `canary`.`user_role` (
    `Id` INT NOT NULL AUTO_INCREMENT,
    `User_Id` INT NOT NULL,
    `Role_Id` INT NOT NULL,
    PRIMARY KEY (`Id`),
    INDEX `FK_User_Role_User_idx` (`User_Id` ASC),
    INDEX `FK_User_Role_Role_idx` (`Role_Id` ASC),
    CONSTRAINT `FK_User_Role_User`
        FOREIGN KEY (`User_Id`)
        REFERENCES `canary`.`user` (`Id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `FK_User_Role_Role`
        FOREIGN KEY (`Role_Id`)
        REFERENCES `canary`.`role` (`Id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);