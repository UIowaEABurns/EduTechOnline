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
	
-- enrolls a user in a course
DROP PROCEDURE IF EXISTS enroll;
CREATE PROCEDURE enroll(IN _u_id INT, IN _c_id INT)
	BEGIN
		INSERT IGNORE INTO course_assoc (user_id, course_id, rating) VALUES (_u_id, _c_id, null);
	END //
	
DROP PROCEDURE IF EXISTS getUsersInCourse;
CREATE PROCEDURE getUsersInCourse(IN _c_id INT)
	BEGIN
		SELECT user_id FROM course_assoc WHERE course_id=_c_id;
	END //
	
DROP PROCEDURE IF EXISTS addQuestion;
CREATE PROCEDURE addQuestion(IN _qid INT, IN _text TEXT, OUT _id INT)
	BEGIN
		INSERT INTO question (topic_id, points,text) VALUES (_qid,1,_text);
		SELECT LAST_INSERT_ID() INTO _id;
	END //
	
DROP PROCEDURE IF EXISTS addAnswer;
CREATE PROCEDURE addAnswer(IN _qid INT, IN _text TEXT, IN _correct boolean, OUT _id INT)
	BEGIN
		INSERT INTO answer (question_id, text,correct) VALUES (_qid,_text,_correct);
		SELECT LAST_INSERT_ID() INTO _id;
	END //
	
DROP PROCEDURE IF EXISTS getAnswersByQuestion;
CREATE PROCEDURE getAnswersByQuestion(IN _qid INT)
	BEGIN
		SELECT * FROM answer WHERE question_id=_qid;
	END //
	
DROP PROCEDURE IF EXISTS getQuestionsByQuiz;
CREATE PROCEDURE getQuestionsByQuiz(IN _qid INT)
	BEGIN
		SELECT * FROM question WHERE topic_id=_qid;
	END //
	
DROP PROCEDURE IF EXISTS getQuizScore;
CREATE PROCEDURE getQuizScore(IN _u INT, IN _q INT)
	BEGIN
		SELECT * FROM quiz_scores WHERE user_id=_u AND quiz_id=_q;
	END //
	
DROP PROCEDURE IF EXISTS addQuizScore;
CREATE PROCEDURE addQuizScore(IN _u INT, IN _q INT, IN _s INT)
	BEGIN
		INSERT IGNORE INTO quiz_scores (user_id, quiz_id, score) VALUES (_u, _q, _s);
	END //
	
DROP PROCEDURE IF EXISTS addQuizAnswer;
CREATE PROCEDURE addQuizAnswer(IN _u INT, IN _a INT)
	BEGIN
		INSERT IGNORE INTO student_answers (user_id, answer_id) VALUES (_u, _a);
	END //
DELIMITER ; --this must be at the end of the file