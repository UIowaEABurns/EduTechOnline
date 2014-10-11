package edutechonline.configuration;

import java.io.File;
import java.lang.reflect.Field;





import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edutechonline.database.Users;
import edutechonline.database.entity.User;
import edutechonline.util.Validator;

/**
 * Class originally written for the StarExec project
 * @author Tyler Jensen
 */

public class ConfigUtil {
	private static final Logger log = Logger.getLogger(ConfigUtil.class);
	
	// XML Metadata to parse config file	
	private static String NODE_CLASS = "class";
	private static String NODE_PROP = "property";
	private static String NODE_CONFIG = "configuration";
	private static String NODE_VALUE = "value";
	private static String ATTR_KEY = "key";
	private static String ATTR_VALUE = "value";
	private static String ATTR_NAME = "name";
	private static String ATTR_DEFAULT = "default";

	
	public static Document getDoc(File configFile) {
		try {
			// Open the config xml file and parse it into a dom			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();			 			
			Document configDoc = db.parse(configFile);
			configDoc.getDocumentElement().normalize();			   			
			return configDoc;

			
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * Loads resources from the config.xml file into the static resource classes
	 * specified in the config file using reflection. The property file keys must match the
	 * corresponding field name in the specified resource class.
	 */
	public static void loadProperties(File configFile){
		try {
			Document configDoc=getDoc(configFile);		   			
			
			// Find the name of the configuration to use
			String defaultConfigName = configDoc.getDocumentElement().getAttributes().getNamedItem(ATTR_DEFAULT).getNodeValue(); 
			log.info("Loading default configuration "+defaultConfigName);

			Node defaultConfigNode = findConfigNode(configDoc.getDocumentElement(), defaultConfigName);
			
			if(defaultConfigNode == null) {
				// If we didn't find a node that matched the specified name, then that's an error!
				throw new Exception(String.format("The default configuration \"%s\" was not found.", defaultConfigName));
			}

			loadPropertiesFromNode(defaultConfigNode);
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}
	
	/**
	 * Tries to get the text content of a singleton child of an Element with the given name.
	 * Returns empty string on error
	 * @param node
	 * @param childName
	 * @return
	 */
	public static String safeGetStringFromChildNode(Element node, String childName) {
		try {
			return node.getElementsByTagName(childName).item(0).getTextContent();
		} catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return "";
	}
	
	public static boolean loadUserFromNode(Node userNode) {
		log.debug("loadUserFromNode called");
		try {
			//this should always be the case
			Element node=(Element) userNode;
			String fName=safeGetStringFromChildNode(node, "fname");
			String lName=safeGetStringFromChildNode(node, "lname");
			String role=safeGetStringFromChildNode(node, "role");
			String pass=safeGetStringFromChildNode(node, "pass");
			String email=safeGetStringFromChildNode(node, "email");
			User u=new User();
			if (!Validator.isValidName(fName) || !Validator.isValidName(lName)) {
				log.error("invalid name for user-- not adding to database "+fName+" "+lName);
				return false;
			}
			if (!Validator.isValidEmail(email)) {
				log.error("invalid email for user-- not adding to database "+email);

				return false;
			}
			if (!Validator.isValidPassword(pass)) {
				log.error("invalid password for user-- not adding to database");
				return false;
			}
			if (!Validator.isValidRole(role)) {
				log.error("invalid role for user-- not adding to database");
				return false;
			}
			u.setFirstName(fName);
			u.setLastName(lName);
			u.setPassword(pass);
			u.setEmail(email);
			u.setRole(role);
			if (Users.getUser(email)!=null) {
				return true; //this user already exists, which is fine.
			}
 			int id=Users.addUser(u);
			if (id<0) {
				log.error("unable to add new user to the database due to a database error");
				return false;
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	/**
	 * Given an XML document, finds all the specified users and loads them into the databse
	 * @return
	 */
	public static boolean loadUsersFromConfig(File configFile) {
		try {
			Document xmlDoc=getDoc(configFile);
			NodeList userNodes=xmlDoc.getElementsByTagName("user");
			for (int x=0;x<userNodes.getLength();x++) {
				boolean success=loadUserFromNode(userNodes.item(x));
				if (!success) {
					log.error("failure adding a user from the config.xml!");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		return false;
	}
	
	/**
	 * Given the root XML element, this method will find the XML node with the given configuration name
	 * @param rootElement The XML document element
	 * @param configName The name of the configuration to find
	 * @return The XML node for the given configuration name
	 */
	private static Node findConfigNode(Element rootElement, String configName) {
		// Get all configuration nodes
		NodeList configNodes = rootElement.getElementsByTagName(NODE_CONFIG);				
		
		// For each of the configuration nodes
		for(int i = 0; i < configNodes.getLength(); i++) {
			Node currentConfig = configNodes.item(i);											
			Node currentConfigNameAttr = currentConfig.getAttributes().getNamedItem(ATTR_NAME);
			
			if(currentConfigNameAttr == null) {
				// If the current node doesn't have a name attribute, skip it
				continue;					
			} else if(currentConfigNameAttr.getNodeValue().equals(configName)) {
				// Otherwise if we've found a config with the name that matches the one to use, keep it and break
				return currentConfig;								
			}
		}
		
		return null; //failed
	}
	
	/**
	 * Given a configuration node, loads all properties into the classes within the node
	 * @param node The configuration node which contains class specifications to load into
	 */
	@SuppressWarnings("rawtypes")
	    private static void loadPropertiesFromNode(Node node) throws Exception {
			Element e=(Element) node;
				
			// Now load the properties from the current node itself
			
			log.debug(String.format("Loading configuration %s", node.getAttributes().getNamedItem(ATTR_NAME)));
	
			//get the class to load our properties into
			Node classNode=e.getElementsByTagName(NODE_CLASS).item(0);
			// Parse the class name from XML attribute and load that class via reflection
			String className = classNode.getAttributes().getNamedItem(ATTR_NAME).getNodeValue();
			Class currentClass = Class.forName(className);
			NodeList classNodeChildren=((Element)classNode).getElementsByTagName(NODE_PROP);

			// For each property node under the current class node...
			for(int j = 0; j < classNodeChildren.getLength(); j++) {
				// Get the property node and parse out the key/value from its attributes
				Node currentPropNode = classNodeChildren.item(j);
				String key = currentPropNode.getAttributes().getNamedItem(ATTR_KEY).getNodeValue();				
				String value = null;
				
				if(currentPropNode.getAttributes().getNamedItem(ATTR_VALUE) != null) {
					// If the property node has a "value" attribute, use that as the node's value
					value = currentPropNode.getAttributes().getNamedItem(ATTR_VALUE).getNodeValue();
				} else {
					throw new Exception("could not find a value for the the property "+key);
				}
											
				log.debug("Setting "+key+" = "+value);
				
				// Get the field from the current class that matches the XML specified key
				Field field = currentClass.getField(key);
				
				// Force the field to be accessible in case it's private or final
				field.setAccessible(true);
				
				// Based on the type of field we're expecting, set that field's value to the property's value
				if(field.getType().equals(String.class)){
					field.set(null, value);
				} else if(field.getType().equals(int.class)){
					field.setInt(null, Integer.parseInt(value));
				} else if(field.getType().equals(long.class)){
					field.setLong(null, Long.parseLong(value));
				} else if(field.getType().equals(boolean.class)){
					field.set(null,Boolean.parseBoolean(value));
				}	            

			}					
	}	
}
