CREATE SCHEMA `movieland` DEFAULT CHARACTER SET utf8 ;
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema movieland
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema movieland
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `movieland` ;
USE `movieland` ;

-- -----------------------------------------------------
-- Table `movieland`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`movie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_russian` VARCHAR(100) NOT NULL,
  `name_native` VARCHAR(100) NOT NULL,
  `released_date` DATE NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `rating` DOUBLE NOT NULL,
  `price` DOUBLE NOT NULL,
  `picture_path` VARCHAR(500) NOT NULL,
  `votes` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movieland`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`genre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movieland`.`movie_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`movie_genre` (
  `movie_id` INT NOT NULL,
  `genre_id` INT NOT NULL,
  INDEX `movie_id_idx` (`movie_id` ASC),
  INDEX `genre_id_idx` (`genre_id` ASC),
  CONSTRAINT `movie_id`
    FOREIGN KEY (`movie_id`)
    REFERENCES `movieland`.`movie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `genre_id`
    FOREIGN KEY (`genre_id`)
    REFERENCES `movieland`.`genre` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movieland`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARBINARY(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movieland`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movie_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `movie_id_idx` (`movie_id` ASC),
  CONSTRAINT `review_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `movieland`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `review_movie_id`
    FOREIGN KEY (`movie_id`)
    REFERENCES `movieland`.`movie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movieland`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movieland`.`movie_country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movieland`.`movie_country` (
  `movie_id` INT NOT NULL,
  `country_id` INT NOT NULL,
  INDEX `country_id_idx` (`country_id` ASC),
  INDEX `movie_id_idx` (`movie_id` ASC),
  CONSTRAINT `movie_country_country_id`
    FOREIGN KEY (`country_id`)
    REFERENCES `movieland`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `movie_country_movie_id`
    FOREIGN KEY (`movie_id`)
    REFERENCES `movieland`.`movie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `movieland`.`rating` (
  `user_id` INT NOT NULL,
  `movie_id` INT NOT NULL,
  `rating` double NOT NULL,
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `movie_id_idx` (`movie_id` ASC),
  CONSTRAINT `rating_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `movieland`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rating_movie_id`
    FOREIGN KEY (`movie_id`)
    REFERENCES `movieland`.`movie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


ALTER TABLE `movieland`.`movie` 
CHANGE COLUMN `released_date` `year_of_release` DATE NOT NULL ;

ALTER TABLE `movieland`.`movie`
CHANGE COLUMN `votes` `votes` INT(11) NOT NULL DEFAULT 0 ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;