package edutechonline.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import edutechonline.database.Courses;
import edutechonline.database.Users;
import edutechonline.database.entity.ContentTopic;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Course;
import edutechonline.database.entity.User;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
public class GenerateCertificate {
	private static Logger log=Logger.getLogger(GenerateCertificate.class);
	//TODO: Maybe make the certificates lok nicer
	public static void makeCertificate(int userId, int courseId, File outputPath) throws FileNotFoundException, DocumentException {
		outputPath.getParentFile().mkdirs();
		log.debug("making a pdf to put at "+outputPath.getAbsolutePath());
		User u=Users.getUser(userId);
		Course c= Courses.getCourse(courseId);
		StringBuilder sb=new StringBuilder();
		sb.append("This document certifies that\n\n");
		sb.append(u.getFullName());
		sb.append("\n\n");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
	
		sb.append("Has completed the following course as of today, "+dateFormat.format(date));
		sb.append("\n\n");
		sb.append(c.getName());
		sb.append("\n\n");
		Float totalGrade=Courses.getCourseGrade(userId, courseId);
		
		
		if (totalGrade!=null) {
			sb.append("The overall grade obtained in this course is "+Util.pointsToGrade(totalGrade));
			sb.append("\n\n");
			sb.append("Individual quiz grades are below\n\n");
			
			for (ContentTopic topic : Courses.getContentTopicsForCourse(c.getID())) {
				if (topic.getType()!=ContentType.QUIZ) {
					continue;
				}
				sb.append(topic.getName()+": "+Util.pointsToGrade(Courses.getQuizScore(topic.getID(), userId))+"\n\n");
			}
		} else {
			sb.append("This is an ungraded course");
		}
		Document document = new Document();
		PdfWriter w= PdfWriter.getInstance(document, new FileOutputStream(outputPath));
		document.open();
		document.addTitle("EduTechOnline Certification");
		Paragraph cert= new Paragraph();
		cert.add(sb.toString());
		document.add(cert);
		document.newPage();
		
		document.close();
		//w.flush();
		//w.close();
		System.out.println(sb.toString());
		
	}
}
