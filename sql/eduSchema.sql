DROP DATABASE IF EXISTS edutech;

CREATE DATABASE edutech;

USE edutech;

-- table holds data for all users

CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(32) NOT NULL,
	last_name VARCHAR(32) NOT NULL,
	email VARCHAR(64) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (email)
);

-- table maps roles to users
CREATE TABLE roles (
	email VARCHAR(64) NOT NULL,
	role CHAR(16),
	CONSTRAINT roles_email FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);

-- Table holds email confirmation codes for all un-confirmed users
CREATE TABLE confirmation (
	user_id INT NOT NULL,
	code CHAR(255) NOT NULL,
	CONSTRAINT confirmation_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- Table holds temporary codes for password reset requests
CREATE TABLE pass_reset (
	user_id INT NOT NULL,
	code CHAR(64) NOT NULL,
	added TIMESTAMP NOT NULL,
	PRIMARY KEY (user_id),
	CONSTRAINT pass_reset_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
