DROP DATABASE IF EXISTS edutech;

CREATE DATABASE edutech;

USE edutech;

-- table holds data for all users

CREATE TABLE users (
	id INT NOT NULL,
	first_name VARCHAR(32) NOT NULL,
	last_name VARCHAR(32) NOT NULL,
	email VARCHAR(64) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (email)
);

-- table maps roles to users
CREATE TABLE roles (
	email VARCHAR(64) NOT NULL,
	role CHAR(16),
	CONSTRAINT roles_email FOREIGN KEY (email) REFERENCES users)email) ON DELETE CASCADE
);

-- Table holds email confirmation codes for all un-confirmed users
CREATE TABLE confirmation (
	user_id INT NOT NULL,
	code CHAR(255) NOT NULL,
	CONSTRAINT confirmation_email FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

