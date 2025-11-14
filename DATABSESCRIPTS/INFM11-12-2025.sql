-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema restaurantdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema restaurantdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `restaurantdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `restaurantdb` ;

-- -----------------------------------------------------
-- Table `restaurantdb`.`loyalty_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`loyalty_members` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `contact_number` VARCHAR(10) NULL DEFAULT NULL,
  `join_date` DATE NULL DEFAULT NULL,
  `points` INT NULL DEFAULT '0',
  `is_active` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`customer_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`menu_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`menu_items` (
  `menu_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `is_available` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`menu_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`tables`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`tables` (
  `table_id` INT NOT NULL AUTO_INCREMENT,
  `capacity` INT NOT NULL,
  `is_available` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`table_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`staff` (
  `staff_id` INT NOT NULL AUTO_INCREMENT,
  `staff_pin` INT NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `contact_number` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`staff_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`order_header`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`order_header` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `table_id` INT NOT NULL,
  `staff_id` INT NOT NULL,
  `order_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `total_cost` DECIMAL(10,2) NULL DEFAULT '0.00',
  `status` ENUM('closed', 'open', 'cancelled') NULL DEFAULT 'open',
  PRIMARY KEY (`order_id`),
  INDEX `table_id` (`table_id` ASC) VISIBLE,
  INDEX `staff_id` (`staff_id` ASC) VISIBLE,
  CONSTRAINT `order_header_ibfk_1`
    FOREIGN KEY (`table_id`)
    REFERENCES `restaurantdb`.`tables` (`table_id`),
  CONSTRAINT `order_header_ibfk_2`
    FOREIGN KEY (`staff_id`)
    REFERENCES `restaurantdb`.`staff` (`staff_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`order_item` (
  `order_item_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `menu_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `subtotal` DECIMAL(10,2) NOT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`order_item_id`),
  INDEX `order_id` (`order_id` ASC) VISIBLE,
  INDEX `menu_id` (`menu_id` ASC) VISIBLE,
  CONSTRAINT `order_item_ibfk_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `restaurantdb`.`order_header` (`order_id`),
  CONSTRAINT `order_item_ibfk_2`
    FOREIGN KEY (`menu_id`)
    REFERENCES `restaurantdb`.`menu_items` (`menu_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`payments` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `amount_paid` DECIMAL(10,2) NOT NULL,
  `staff_id` INT NOT NULL,
  `loyal_customer_id` INT NULL DEFAULT NULL,
  `unknown_customer_name` VARCHAR(50) NULL DEFAULT NULL,
  `payment_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_method` ENUM('CASH', 'DEBIT', 'CREDIT') NOT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`transaction_id`, `order_id`),
  INDEX `order_id` (`order_id` ASC) VISIBLE,
  INDEX `staff_id` (`staff_id` ASC) VISIBLE,
  INDEX `loyal_customer_id` (`loyal_customer_id` ASC) VISIBLE,
  CONSTRAINT `payments_ibfk_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `restaurantdb`.`order_header` (`order_id`),
  CONSTRAINT `payments_ibfk_2`
    FOREIGN KEY (`staff_id`)
    REFERENCES `restaurantdb`.`staff` (`staff_id`),
  CONSTRAINT `payments_ibfk_3`
    FOREIGN KEY (`loyal_customer_id`)
    REFERENCES `restaurantdb`.`loyalty_members` (`customer_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`reservations` (
  `request_id` INT NOT NULL AUTO_INCREMENT,
  `table_id` INT NOT NULL,
  `reserve_name` VARCHAR(50) NOT NULL,
  `date_and_time` DATETIME NOT NULL,
  `subtotal` DECIMAL(10,2) NULL DEFAULT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`request_id`),
  INDEX `table_id` (`table_id` ASC) VISIBLE,
  CONSTRAINT `reservations_ibfk_1`
    FOREIGN KEY (`table_id`)
    REFERENCES `restaurantdb`.`tables` (`table_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurantdb`.`staff_tracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurantdb`.`staff_tracker` (
  `staff_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `time_in` DATETIME NULL DEFAULT NULL,
  `time_out` DATETIME NULL DEFAULT NULL,
  `session_minutes` INT NULL DEFAULT NULL,
  PRIMARY KEY (`staff_id`, `date`),
  CONSTRAINT `staff_tracker_ibfk_1`
    FOREIGN KEY (`staff_id`)
    REFERENCES `restaurantdb`.`staff` (`staff_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
