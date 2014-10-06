USE edutech;

DELIMITER // -- this goes at the end of every procedure

DROP PROCEDURE IF EXISTS getUser;
CREATE PROCEDURE getUser(IN _id INT)
	BEGIN
		SELECT * FROM users WHERE id=_id;
	END //
	
DROP PROCEDURE IF EXISTS getAllUsers;
CREATE PROCEDURE getAllUsers()
	BEGIN
		SELECT * FROM users;
	END //
	
DELIMITER ; --this must be at the end of the file