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
		SELECT * FROM courses WHERE owner_id=_id;
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
	
	
-- Removes the given course from the database
DROP PROCEDURE IF EXISTS deleteContentTopic;
CREATE PROCEDURE deleteContentTopic(IN _id INT)
	BEGIN
		DELETE FROM content_topics WHERE id=_id;
	END //
	
-- adds a new course into the database
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS addCourse;
CREATE PROCEDURE addCourse(IN _n VARCHAR(64), IN _d TEXT, IN _o INT, IN _c DOUBLE, IN _r VARCHAR(32), IN _op BOOLEAN,IN _deprecated BOOLEAN, OUT _id INT)
BEGIN
	INSERT INTO courses (name,description,owner_id,cost,open,category,deprecated) VALUES (_n, _d, _o, _c, _op,_r,_deprecated);
	SELECT LAST_INSERT_ID() INTO _id;

END //

-- adds a new course into the database
-- Author: Eric Burns
DROP PROCEDURE IF EXISTS addContentTopic;
CREATE PROCEDURE addContentTopic(IN _n VARCHAR(64), IN _d TEXT, IN _cid INT, IN _url TEXT, IN _t INT, OUT _id INT)
BEGIN
	INSERT INTO content_topics (name,description,course_id,url,topic_type) VALUES (_n, _d, _cid, _url,_t);
	SELECT LAST_INSERT_ID() INTO _id;

END //


DROP PROCEDURE IF EXISTS editCourseVisibility;
CREATE PROCEDURE editCourseVisibility(IN _id INT, IN _visible BOOLEAN)
	BEGIN
		UPDATE courses SET open=_visible WHERE id=_id;
	END //
	
DROP PROCEDURE IF EXISTS editCourseDeprecation;
CREATE PROCEDURE editCourseDeprecation(IN _id INT, IN _visible BOOLEAN)
	BEGIN
		UPDATE courses SET deprecated=_visible WHERE id=_id;
	END //


-- gets a single content topic
DROP PROCEDURE IF EXISTS getContentTopic;
CREATE PROCEDURE getContentTopic(IN _id INT)
	BEGIN
		SELECT * from content_topics WHERE id=_id;
	END //
	
-- gets all content topics for a course
DROP PROCEDURE IF EXISTS getCourseTopics;
CREATE PROCEDURE getCourseTopics(IN _id INT)
	BEGIN
		SELECT * from content_topics WHERE course_id=_id ORDER BY id;
	END //
DELIMITER ; --this must be at the end of the file