CREATE DATABASE IF NOT EXISTS mysns
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE mysns;

CREATE TABLE IF NOT EXISTS user(
	id VARCHAR(128) PRIMARY KEY,
	password VARCHAR(32),
	name VARCHAR(32),
	ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	desc VARCHAR(128),
	image VARCHAR(1024),
	friends JSON
);

CREATE TABLE IF NOT EXISTS feed(
	no INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	id VARCHAR(128),
	content VARCHAR(4096),
	ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	images VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS friend(
	id VARCHAR(128),
	frid VARCHAR(128),
	INDEX idx1(id)
);

CREATE TABLE IF NOT EXISTS `like`(
	id VARCHAR(128),
	no VARCHAR(128),
	INDEX idx1(id)
);