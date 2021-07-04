SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';



SHOW WARNINGS;

DROP SCHEMA IF EXISTS `dom` ;

CREATE SCHEMA IF NOT EXISTS `dom` DEFAULT CHARACTER SET latin1 ;

SHOW WARNINGS;

USE `dom` ;



-- -----------------------------------------------------

-- Table `object`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `object` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `object` (

  `ObjectName` VARCHAR(45) NOT NULL ,

  `ObjectType` ENUM('xpath','id','name','classname','paritallinktext','linktext','tagname','angularbuttontext','repeater','exactrepeater','binding','exactbinding','model','options','partialbuttontext','  csscontainingtext') NOT NULL ,

  `ObjectValue` MEDIUMTEXT NOT NULL ,

  PRIMARY KEY (`ObjectName`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = latin1;



SHOW WARNINGS;





SET SQL_MODE=@OLD_SQL_MODE;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

