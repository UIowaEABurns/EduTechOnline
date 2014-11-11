package edutechonline.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edutechonline.database.entity.Answer;
import edutechonline.database.entity.ContentTopic.ContentType;
import edutechonline.database.entity.Question;
import edutechonline.database.entity.Quiz;

public class QuizXMLReader {
	private static Logger log=Logger.getLogger(QuizXMLReader.class);
	
	public static Quiz readQuizXML(String xml) {
		try {
			Quiz q=new Quiz();
			q.setType(ContentType.QUIZ);
			
			DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc=builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
			
			
			Node node=doc.getElementsByTagName("Quiz").item(0);
			
			q.setName(node.getAttributes().getNamedItem("name").getNodeValue());
			q.setDescription(node.getAttributes().getNamedItem("description").getNodeValue());
			q.setCourseId(Integer.parseInt(node.getAttributes().getNamedItem("course").getNodeValue()));
			
			NodeList questions=node.getChildNodes();
			for (int x=0;x<questions.getLength();x++) {
				
				Node question=questions.item(x);
				if (!question.getNodeName().equals("Question")) {
					continue;
				}
				Question quest=new Question();
				quest.setText(question.getAttributes().getNamedItem("text").getNodeValue());
				
				NodeList answers=question.getChildNodes();
				
				for (int y=0;y<answers.getLength();y++) {
					Node answer=answers.item(y);
					if (!answer.getNodeName().equals("Answer")) {
						continue;
					}
					Answer a=new Answer();
					a.setText(answer.getTextContent());
					a.setCorrect(Boolean.parseBoolean(answer.getAttributes().getNamedItem("correct").getNodeValue()));
					quest.addAnswer(a);
				}
				
				q.addQuestion(quest);
				
			}
			return q;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		
		return null;
	}
	
	public static Quiz readQuizXML(File xmlFile) throws IOException{
		return readQuizXML(FileUtils.readFileToString(xmlFile));
	}
}
