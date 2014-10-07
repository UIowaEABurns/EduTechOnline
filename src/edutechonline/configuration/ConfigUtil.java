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
	
	// The configuration in use
	private static String configName = "";
	
	/**
	 * Loads resources from the config.xml file into the static resource classes
	 * specified in the config file using reflection. The property file keys must match the
	 * corresponding field name in the specified resource class.
	 */
	public static void loadProperties(File configFile){
		try {
			// Open the config xml file and parse it into a dom			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();			 			
			Document configDoc = db.parse(configFile);
			configDoc.getDocumentElement().normalize();			   			
			
			if(configDoc.getDocumentElement().getAttributes().getNamedItem(ATTR_DEFAULT) == null) {
				// Check if the default configuration is specified! We explicitly require it
				throw new Exception(String.format("config parsing error: the root element must define an attribute \"%s\"", ATTR_DEFAULT));
			}
			
			// Find the name of the configuration to use
			String defaultConfigName = configDoc.getDocumentElement().getAttributes().getNamedItem(ATTR_DEFAULT).getNodeValue(); 
			log.info("Loading default configuration "+defaultConfigName);

			Node defaultConfigNode = findConfigNode(configDoc.getDocumentElement(), defaultConfigName);
			
			if(defaultConfigNode == null) {
				// If we didn't find a node that matched the specified name, then that's an error!
				throw new Exception(String.format("The default configuration \"%s\" was not found.", defaultConfigName));
			}

			loadPropertiesFromNode(configDoc, defaultConfigNode);
			ConfigUtil.configName = defaultConfigName;
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
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
		
		// The config node to find
		Node configNode = null;				
		
		// For each of the configuration nodes
		for(int i = 0; i < configNodes.getLength(); i++) {
			Node currentConfig = configNodes.item(i);											
			Node currentConfigNameAttr = currentConfig.getAttributes().getNamedItem(ATTR_NAME);
			
			if(currentConfigNameAttr == null) {
				// If the current node doesn't have a name attribute, skip it
				continue;					
			} else if(currentConfigNameAttr.getNodeValue().equals(configName)) {
				// Otherwise if we've found a config with the name that matches the one to use, keep it and break
				configNode = currentConfig;								
				break;
			}
		}
		
		return configNode;
	}
	
	/**
	 * Given a configuration node, loads all properties into the classes within the node
	 * @param node The configuration node which contains class specifications to load into
	 */
	@SuppressWarnings("rawtypes")
	    private static void loadPropertiesFromNode(Document configDoc, Node node) throws Exception {

			
		// Now load the properties from the current node itself
		
		log.debug(String.format("Loading configuration %s", node.getAttributes().getNamedItem(ATTR_NAME)));

		// Get all subnodes from the given node
		NodeList classNodes = node.getChildNodes();		
		
		// For each class node in the configuration...
		for(int i = 0; i < classNodes.getLength(); i++) {
			// Get that class node and its child nodes
			Node currentClassNode = classNodes.item(i);				
			
			if(!currentClassNode.getNodeName().equals(NODE_CLASS)){
				// If we're not looking at class node (most likely an attribute) skip
				continue;
			}
			
			NodeList classNodeChildren = currentClassNode.getChildNodes();
			
			// Parse the class name from XML attribute and load that class via reflection
			String className = currentClassNode.getAttributes().getNamedItem(ATTR_NAME).getNodeValue();
			Class currentClass = Class.forName(className);
			
			log.debug("Loading class "+className);

			// For each property node under the current class node...
			for(int j = 0; j < classNodeChildren.getLength(); j++) {
				// Get the property node and parse out the key/value from its attributes
				Node currentPropNode = classNodeChildren.item(j);
				
				if(!currentPropNode.getNodeName().equals(NODE_PROP)){
					// If we're not looking at property node skip
					continue;
				}
				
				String key = currentPropNode.getAttributes().getNamedItem(ATTR_KEY).getNodeValue();				
				String value = null;
				
				if(currentPropNode.getAttributes().getNamedItem(ATTR_VALUE) != null) {
					// If the property node has a "value" attribute, use that as the node's value
					value = currentPropNode.getAttributes().getNamedItem(ATTR_VALUE).getNodeValue();
				} else {
					NodeList valueNodes = currentPropNode.getChildNodes();
					Node valueNode = null;
					for(int k = 0; k < valueNodes.getLength(); k++) {
						if(!valueNodes.item(k).getNodeName().equals(NODE_VALUE)) {
							continue;
						}	
						
						valueNode = valueNodes.item(k);
						break;
					}
					
					if(valueNode != null) {
						// Or else it may be contained within the node as CDATA					
						value = ((CharacterData)valueNode.getFirstChild()).getData();	
					} else {
						throw new Exception("Expected CDATA value but none was specified");
					}
				}
											
				log.debug("Setting "+key+" = "+value);


				try {
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
					
				} catch (Exception e){
					log.error(String.format("Failed to load property [%s]. Error [%s]", key, e.getMessage()));
				}
			}				
		}	
	}
	
	/**
	 * @return The name of the currently loaded configuration
	 */
	public static String getConfigName() {						
		return ConfigUtil.configName;
	}
	
	
}