package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PoolPartyReader {

	private String ppExportFileName;
	
	public PoolPartyReader(String ppExportFileName) {
		this.ppExportFileName = ppExportFileName;
	}
	
	/**
	 * This function will open the PoolParty exported XML file and will get the 
	 * list of all documents that were added to the PP Project.  It will return 
	 * the list of the documents with the associated "urn" so that the document
	 * can be found again in the PP file.  The format of each item in the list 
	 * will be: 
	 * 		<value of retrieved_from tag>#*#<urn>
	 * example:
	 * 		http://www.acutecprecision.com/#*#urn:uuid:620a1543-d4a4-4c11-9642-f1a56c2161c8
	 * @return
	 */
	public List<String> getDocumentList() {
		List<String> documents = new ArrayList<String>();
		
		try {
			// Open the PoolParty XML file
			File poolPartyFile = new File(ppExportFileName);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(poolPartyFile);
			
			// Get the "retrieved_from" nodes
			NodeList retrievedFrom = doc.getElementsByTagName("retrieved_from");
			for (int x=0; x < retrievedFrom.getLength(); x++) {
				Node thisTag = retrievedFrom.item(x);
				NamedNodeMap attribs = thisTag.getAttributes();
				Node resource = attribs.getNamedItem("rdf:resource");
				String thisDocument = resource.getNodeValue();
				Node description = thisTag.getParentNode();
				attribs = description.getAttributes();
				Node about = attribs.getNamedItem("rdf:about");
				thisDocument += "#*#"+about.getNodeValue();
				documents.add(thisDocument);
			}  // end for each retrievedFrom tag
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return documents;
	}  // end getDocumentList()

}
