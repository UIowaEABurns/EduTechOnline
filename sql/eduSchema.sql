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

CREATE TABLE courses (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(64),
	description TEXT,
	cost DOUBLE NOT NULL, -- how much does it cost, in dollars, to enroll in the course
	owner_id INT, -- course manager that created this course
	open BOOLEAN NOT NULL,
	deprecated BOOLEAN NOT NULL,
	category VARCHAR(32),
	PRIMARY KEY (id),
	CONSTRAINT courses_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL 

);

CREATE TABLE content_topics (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(64),
	description TEXT,
	course_id INT, -- course this topic belongs to
	url TEXT, -- url to this content, which may or may not be set
	topic_type INT,
	PRIMARY KEY (id),
	CONSTRAINT content_topics_course_id FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE

);

-- these are the questions we will present during a quiz
CREATE TABLE question(
	id INT AUTO_INCREMENT,
	topic_id INT,
	points INT,
	text TEXT,
	PRIMARY KEY(id),
	CONSTRAINT qustion_topic_id FOREIGN KEY (topic_id) REFERENCES content_topics(id) ON DELETE CASCADE
);

-- these are the multiple choice answers that we will present to users during quizzes
CREATE TABLE answer(
	id INT AUTO_INCREMENT,
	question_id INT,
	text TEXT,
	correct boolean,
	PRIMARY KEY(id),
	CONSTRAINT answer_question_id FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

-- represents a user taking a course
CREATE TABLE course_assoc (
	user_id INT NOT NULL,
	course_id INT NOT NULL,
	rating INT, -- how much does the user like the course?
	PRIMARY KEY (user_id, course_id),-- one rating per student in a course
	CONSTRAINT course_assoc_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	CONSTRAINT course_assoc_course_id FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

CREATE TABLE quiz_scores (
	quiz_id INT NOT NULL,
	user_id INT NOT NULL,
	score INT,
	CONSTRAINT quiz_scores_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	CONSTRAINT quiz_scores_quiz_id FOREIGN KEY (quiz_id) REFERENCES content_topics(id) ON DELETE CASCADE
);
