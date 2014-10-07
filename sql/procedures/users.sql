USE edutech;

DELIMITER // -- this goes at the end of every procedure

-- Gets a user by their user id
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getUser;
CREATE PROCEDURE getUser(IN _id INT)
	BEGIN
		SELECT * FROM users WHERE id=_id;
	END //
	
-- Gets every user from the database
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getAllUsers;
CREATE PROCEDURE getAllUsers()
	BEGIN
		SELECT * FROM users;
	END //
	
-- Gets a user by their email address
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS getUserByEmail;
CREATE PROCEDURE getUserByEmail(IN _email VARCHAR(64))
	BEGIN
		SELECT * FROM users WHERE email=_email;
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

DROP PROCEDURE IF EXISTS getUserByCode;
CREATE PROCEDURE getUserByCode(IN _code VARCHAR(255))
	BEGIN
		SELECT user_id FROM confirmation WHERE code=_code;
	END //
	
DROP PROCEDURE IF EXISTS verifyUser;
CREATE PROCEDURE verifyUser(IN _userId INT, IN _email VARCHAR(64), IN _role VARCHAR(16))
	BEGIN
		DELETE FROM confirmation WHERE user_id=_userId;
		UPDATE roles SET role=_role WHERE email=_email;
	END //
DELIMITER ; --this must be at the end of the file