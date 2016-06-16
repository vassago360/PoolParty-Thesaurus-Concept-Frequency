package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLtoCSV {

	private static String translatorXMLFileName = "TranslatorMapping.xml";
	private static String translatorCSVFileName = "TranslatorMapping.csv";
	private static List<TranslatorMapping> translatorMappings;
	
	/**
	 * This is a quick 'script' to convert he TranslatorMapping.xml file into a CSV file.  
	 * The CSV file can then be used to create a more human-readable document of the mappings.
	 * 
	 */
	public static void main(String[] args) {
		loadTranslatorMappings();  // Reusing previously written code to parse the XML file.
		saveCSVFile();
	}

	private static void loadTranslatorMappings() {
		// Create a new mapping array.
		translatorMappings = new ArrayList();
		
		try {
			// Open the translator mapping file.
			File translatorFile = new File(translatorXMLFileName);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(translatorFile);
			
			// For each concept in the mapping file.
			NodeList concepts = doc.getElementsByTagName("Concept");
			for (int x = 0; x < concepts.getLength(); x++) {
				Node thisConcept = concepts.item(x);
				NodeList childNodes = thisConcept.getChildNodes();
				
				// Create a new TranslatorMapping object.
				TranslatorMapping thisMapping = new TranslatorMapping();
				
				for (int y=0; y < childNodes.getLength(); y++) {
					Node childNode = childNodes.item(y);
					if (childNode.getNodeName().equalsIgnoreCase("#text"))
						continue;  // ignore text nodes, they aren't used.
					
					// Get the Thesaurus value & add to object.
					if (childNode.getNodeName().equalsIgnoreCase("Thesaurus")) {
						NamedNodeMap attribs = childNode.getAttributes();
						Node value = attribs.getNamedItem("value");
						thisMapping.setPpConceptTag(value.getNodeValue());
						continue;
					}

					// Get the children of the MSDL node.
					NodeList msdlChildNodes = childNode.getChildNodes();
					for (int z=0; z < msdlChildNodes.getLength(); z++) {
						Node msdlChild = msdlChildNodes.item(z);
						if (msdlChild.getNodeName().equals("#text"))
							continue;
						NamedNodeMap attribs = msdlChild.getAttributes();
						Node value = attribs.getNamedItem("value");
						
						if (msdlChild.getNodeName().equals("name")) {
							// Get the MSDL name value & add to object.
							thisMapping.setMsdlName(value.getNodeValue());
						} else if (msdlChild.getNodeName().equals("type")) {
							// Get the MSDL type value & add to object.
							thisMapping.setMsdlType(value.getNodeValue());
						} else if (msdlChild.getNodeName().equals("URI")) {
							// 	Get the MSDL URI value & add to object.
							thisMapping.setMsdlURI(value.getNodeValue());
						} else if (msdlChild.getNodeName().equals("attribute")) {
							// Get the MSDL attribute value (if it exists) & add to object.
							thisMapping.setMsdlAttribute(value.getNodeValue());
						}
					}  // end for each MSDL child node.
				}  // end for each of the concept's child nodes
				
				// Add the translator mapping object to the main mapping array.
				translatorMappings.add(thisMapping);
			}  // end for each of the concepts in the mapping file.
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}  // end loadTranslatorMappings()
	
	private static void saveCSVFile() {
		File outputFile = new File(translatorCSVFileName);
		Writer writer;
		try {
			writer = new BufferedWriter(new FileWriter(outputFile));
		} catch (IOException e) {
			System.err.println("Error opening the output file.");
			e.printStackTrace();
			return;
		}
		
		Iterator<TranslatorMapping> itr = translatorMappings.iterator();
		while (itr.hasNext()) {
			TranslatorMapping mapping = itr.next();
			try {
				writer.write(mapping.getPpConceptTag()+","+mapping.getMsdlName()+System.getProperty("line.separator"));
			} catch (IOException e) {
				System.err.println("Error writing to CSV file.");
				e.printStackTrace();
			}
		}  // end while itr translatorMappings
		
		try {
			writer.close();
		} catch (IOException e) {
			System.err.println("Error closing the CSV file.");
			e.printStackTrace();
		}
	}  // end saveCSVFile()
}
