USE edutech;

DELIMITER // -- this goes at the end of every procedure

-- Gets a user by their user id
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getUser;
CREATE PROCEDURE getUser(IN _id INT)
	BEGIN
		SELECT * FROM users
		JOIN roles ON roles.email=users.email
		WHERE id=_id;
	END //
	
-- Gets every user from the database
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getAllUsers;
CREATE PROCEDURE getAllUsers()
	BEGIN
		SELECT * FROM users
		JOIN roles ON roles.email=users.email;
	END //
	
-- Gets a user by their email address
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getUserByEmail;
CREATE PROCEDURE getUserByEmail(IN _email VARCHAR(64))
	BEGIN
		SELECT * FROM users 
		JOIN roles ON roles.email=users.email

		WHERE users.email=_email;
	END //
	
	
-- registers a new user for the service
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS addUser;
CREATE PROCEDURE addUser(IN _fn VARCHAR(32), IN _ln VARCHAR(32), IN _e VARCHAR(64), IN _p VARCHAR(255), IN _r VARCHAR(255), OUT _id INT)
BEGIN
	INSERT INTO users (first_name,last_name,email,password) VALUES (_fn, _ln, _e, _p);
	SELECT LAST_INSERT_ID() INTO _id;

	INSERT INTO roles (email, role) VALUES (_e,_r);
END //
	
-- registers a new user for the service
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS registerNewUser;
CREATE PROCEDURE registerNewUser(IN _fn VARCHAR(32), IN _ln VARCHAR(32), IN _e VARCHAR(64), IN _p VARCHAR(255), IN _c VARCHAR(255), OUT _id INT)
BEGIN
	INSERT INTO users (first_name,last_name,email,password) VALUES (_fn, _ln, _e, _p);
	SELECT LAST_INSERT_ID() INTO _id;

	INSERT INTO roles (email, role) VALUES (_e,"unverified");
	INSERT INTO confirmation (user_id,code) VALUES (_id,_c);
END //

-- Gets the ID of a user given a code in the confirmation table
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getUserByCode;
CREATE PROCEDURE getUserByCode(IN _code VARCHAR(255))
	BEGIN
		SELECT user_id FROM confirmation WHERE code=_code;
	END //
	
-- removes a user from the confirmation table and gives them a new role
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS verifyUser;
CREATE PROCEDURE verifyUser(IN _userId INT, IN _email VARCHAR(64), IN _role VARCHAR(16))
	BEGIN
		DELETE FROM confirmation WHERE user_id=_userId;
		UPDATE roles SET role=_role WHERE email=_email;
	END //
	
	
-- updates the role of the user with the given email address
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS updateRole;
CREATE PROCEDURE updateRole(IN _email VARCHAR(64), IN _role VARCHAR(16))
	BEGIN
		UPDATE roles SET role=_role WHERE email=_email;
	END //
	
-- Deletes the given user from the database permanently
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS deleteUser;
CREATE PROCEDURE deleteUser(IN _userId INT)
	BEGIN
		DELETE FROM users WHERE id=_userId;
	END //
	
-- Adds a new password reset to this user
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS addUserPassReset;
CREATE PROCEDURE addUserPassReset(IN _id INT, IN _code VARCHAR(64))
	BEGIN
		INSERT IGNORE INTO pass_reset (user_id, code, added) VALUES (_id, _code, CURRENT_TIMESTAMP);	
	END //
	
-- deletes teh pass_reset entry for the given user
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS removeUserPassReset;
CREATE PROCEDURE removeUserPassReset(IN _id INT)
	BEGIN
		DELETE FROM pass_reset WHERE user_id=_id;
	END //
	
-- Gets the pass_reset entry for the given user
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getUserPassReset;
CREATE PROCEDURE getUserPassReset(IN _id INT)
	BEGIN
		SELECT * FROM pass_reset WHERE user_id=_id;
	END //
	
-- Removes entries from the password reset table older than the given date
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS clearPassResetEntriesOlderThan;
CREATE PROCEDURE clearPassResetEntriesOlderThan(IN _time TIMESTAMP)
	BEGIN
		DELETE FROM pass_reset WHERE added < _time;
	END //
	
DROP PROCEDURE IF EXISTS updatePassword;
CREATE PROCEDURE updatePassword(IN _id INT, IN _pass VARCHAR(255))
	BEGIN
		UPDATE users SET password=_pass WHERE id=_id;
	END //
	
DROP PROCEDURE IF EXISTS updateUser;
CREATE PROCEDURE updateUser(IN _id INT, IN _fname VARCHAR(32), IN  _lname VARCHAR(32))
	BEGIN
		UPDATE users SET first_name=_fname, last_name=_lname
		WHERE id=_id;
	END //
DELIMITER ; --this must be at the end of the file