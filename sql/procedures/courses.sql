USE edutech;

DELIMITER // -- this goes at the end of every procedure

-- Retrieves the given course from the database
DROP PROCEDURE IF EXISTS getCourse;
CREATE PROCEDURE getCourse(IN _id INT)
	BEGIN
		SELECT * FROM courses WHERE id=_id;
	END //
	
-- Retrieves the given course from the database
DROP PROCEDURE IF EXISTS getCoursesByManager;
CREATE PROCEDURE getCoursesByManager(IN _id INT)
	BEGIN
		SELECT * FROM courses WHERE owner=_id;
	END //
	
-- Retrieves the given course from the database
DROP PROCEDURE IF EXISTS getAllCourses;
CREATE PROCEDURE getAllCourses()
	BEGIN
		SELECT * FROM courses;
	END //
	
-- Retrieves the given course from the database
DROP PROCEDURE IF EXISTS getAllOpenCourses;
CREATE PROCEDURE getAllOpenCourses()
	BEGIN
		SELECT * FROM courses where open=true;
	END //
	
-- Removes the given course from the database
DROP PROCEDURE IF EXISTS deleteCourse;
CREATE PROCEDURE deleteCourse(IN _id INT)
	BEGIN
		DELETE FROM courses WHERE id=_id;
	END //
	
-- adds a new course into the database
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS addCourse;
CREATE PROCEDURE addCourse(IN _n VARCHAR(64), IN _d TEXT, IN _o INT, IN _c DOUBLE, IN _r VARCHAR(255), IN _op BOOLEAN, OUT _id INT)
BEGIN
	INSERT INTO courses (name,description,owner,cost,open) VALUES (_n, _ln, _o, _d, _op);
	SELECT LAST_INSERT_ID() INTO _id;

END //

DELIMITER ; --this must be at the end of the file