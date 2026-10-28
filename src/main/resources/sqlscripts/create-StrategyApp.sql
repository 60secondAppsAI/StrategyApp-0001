CREATE DATABASE IF NOT EXISTS StrategyApp;

CREATE TABLE StrategyApp.strategys (
    `strategy_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `description` VARCHAR(255),
    `strategy_type` VARCHAR(255),
    `status` VARCHAR(255),
PRIMARY KEY (strategy_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE StrategyApp.strategy_parameters (
    `strategy_parameter_id` INT AUTO_INCREMENT,
    `strategy_id` INT,
    `parameter_name` VARCHAR(255),
    `parameter_value` VARCHAR(255),
    `data_type` VARCHAR(255),
PRIMARY KEY (strategy_parameter_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE StrategyApp.strategy_instruments (
    `strategy_instrument_id` INT AUTO_INCREMENT,
    `strategy_id` INT,
    `instrument_id` INT,
    `active` BOOLEAN,
PRIMARY KEY (strategy_instrument_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE StrategyApp.strategy_versions (
    `strategy_version_id` INT AUTO_INCREMENT,
    `strategy_id` INT,
    `version_number` VARCHAR(255),
    `created_at` DATETIME,
    `status` VARCHAR(255),
PRIMARY KEY (strategy_version_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
