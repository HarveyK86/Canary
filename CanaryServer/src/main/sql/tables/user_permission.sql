CREATE TABLE `canary`.`user_permission` (
    `Id` INT NOT NULL AUTO_INCREMENT,
    `User_Id` INT NOT NULL,
    `Permission_Id` INT NOT NULL,
    PRIMARY KEY (`Id`),
    INDEX `FK_User_Permission_User_idx` (`User_Id` ASC),
    INDEX `FK_User_Permission_Permission_idx` (`Permission_Id` ASC),
    CONSTRAINT `FK_User_Permission_User`
        FOREIGN KEY (`User_Id`)
        REFERENCES `canary`.`user` (`Id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `FK_User_Permission_Permission`
        FOREIGN KEY (`Permission_Id`)
        REFERENCES `canary`.`permission` (`Id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);