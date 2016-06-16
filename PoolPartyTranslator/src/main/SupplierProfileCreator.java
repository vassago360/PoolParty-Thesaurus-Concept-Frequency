package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import msdlWriters.SupplierProfileWriter;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SupplierProfileCreator {

	private String translatorXMLFileName;
	private String ppOutputFileName;
	private String translatorOutputFileName;
	private String baseMSDLURL;
	private String supplierName;
	private String documentURN;
	private String sourceDocumentText;
	private List translatorMappings;
	private List poolPartyTaggings;	
	public List<String> ServiceConceptsFound = new ArrayList<String>();
	public List<String> MaterialConceptsFound = new ArrayList<String>();
	public List<String> IndustryConceptsFound = new ArrayList<String>();
	public List<String> ProcessConceptsFound = new ArrayList<String>();
	public List<String> OtherConceptsFound = new ArrayList<String>();
	public List<String> terms = new ArrayList<String>();
	
	public SupplierProfileCreator(String translatorXMLFileName, String ppOutputFileName,
			String translatorOutputFileName, String baseMSDLURL, String supplierName, String documentURN) {
		this.translatorXMLFileName = translatorXMLFileName;
		this.ppOutputFileName = ppOutputFileName;
		this.translatorOutputFileName = translatorOutputFileName;
		this.baseMSDLURL = baseMSDLURL;
		this.supplierName = supplierName;
		this.documentURN = documentURN;
	}  // end constructor
	
	public SupplierProfileCreator(String sourceDocumentText) {
		this.sourceDocumentText = sourceDocumentText;
	}
	
	public SupplierProfileCreator(String translatorXMLFileName, String sourceDocumentText, String outputFileName,
			String baseMSDLURL, String supplierName) {
		this.translatorXMLFileName = translatorXMLFileName;
		this.ppOutputFileName = null;
		this.translatorOutputFileName = outputFileName;
		this.baseMSDLURL = baseMSDLURL;
		this.supplierName = supplierName;
		this.documentURN = null;
		this.sourceDocumentText = sourceDocumentText;
		
	}  // end constructor
	
	public SupplierProfileCreator(String sourceDocumentText, String outputFileName, 
			String baseMSDLURL, String supplierName) {
		this.translatorXMLFileName = null;
		this.ppOutputFileName = null;
		this.translatorOutputFileName = outputFileName;
		this.baseMSDLURL = baseMSDLURL;
		this.supplierName = supplierName;
		this.documentURN = null;
		this.sourceDocumentText = sourceDocumentText;
		
	}  // end constructor
	
	public List<String> getPPXconcepts() {
		// Use the PPX API to get the taggings
		WebRetriever web = new WebRetriever(WebRetriever.RetrieveType.PPX);

		// To make best use of PPX there is a limit to how much text it can receive 
		// and provide maximal concepts returned.  Through testing I've determined it
		// to be around 4000 characters.  Therefore, the document text is split up
		// into pieces of 4000.
		poolPartyTaggings = new ArrayList<String>();
		int pieces = sourceDocumentText.length() / 60000;
		int index = 0;
		for (int x=0; x < pieces; x++) {
			System.out.println("iterate " + x);
			poolPartyTaggings.addAll(web.queryPPX(sourceDocumentText.substring(index, index+59999)));
			index += 60000;
		}
		poolPartyTaggings.addAll(web.queryPPX(sourceDocumentText.substring(index)));
		//save terms retrieved
		terms = web.terms;
		//remove any duplicates
		List<String> poolPartyTaggingsWOutDuplicates = new ArrayList<String>();    
		for (String tag: (List<String>) poolPartyTaggings)
		{
		  if (!poolPartyTaggingsWOutDuplicates.contains(tag)) 
		  {
			  poolPartyTaggingsWOutDuplicates.add(tag);
		  }
		 }
		
		return poolPartyTaggingsWOutDuplicates;
	}
	
	public void createSupplierProfile() {
		
		if (translatorXMLFileName != null) {
			// Load the translator mappings
			loadTranslatorMappings();
		} else {
			translatorMappings = null;
		}
		
		if (ppOutputFileName != null) {
			// Load the PoolParty taggings
			loadPoolPartyTaggings();
		} else {
			// Use the PPX API to get the taggings
			WebRetriever web = new WebRetriever(WebRetriever.RetrieveType.PPX);

			// To make best use of PPX there is a limit to how much text it can receive 
			// and provide maximal concepts returned.  Through testing I've determined it
			// to be around 4000 characters.  Therefore, the document text is split up
			// into pieces of 4000.
			poolPartyTaggings = new ArrayList<String>();
			int pieces = sourceDocumentText.length() / 60000;
			int index = 0;
			for (int x=0; x < pieces; x++) {
				poolPartyTaggings.addAll(web.queryPPX(sourceDocumentText.substring(index, index+59999)));
				index += 60000;
			}
			poolPartyTaggings.addAll(web.queryPPX(sourceDocumentText.substring(index)));
			
			//Whenever web.queryPPX is called ServiceConceptsFound, MaterialConceptsFound, ... are updated
			ServiceConceptsFound = web.ServiceConceptsFound;
			MaterialConceptsFound = web.MaterialConceptsFound;
			IndustryConceptsFound = web.IndustryConceptsFound;
			ProcessConceptsFound = web.ProcessConceptsFound;
			OtherConceptsFound = web.OtherConceptsFound;
		}
		// Create hierarchy from PPX SPARQL endpoint (not going to use this)
		// getServiceMaterialIndustryProcessOtherLists(poolPartyTaggings);
		
		// Create the MSDL profile
		System.out.println(poolPartyTaggings);
		SupplierProfileWriter spWriter = new SupplierProfileWriter(supplierName, translatorOutputFileName, baseMSDLURL,
				translatorMappings, poolPartyTaggings); 
		spWriter.createProfile();
		
	}  // end createSupplierProfile()
	
	private void loadTranslatorMappings() {
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
	
	private void loadPoolPartyTaggings() {
		// Create a new list of PoolParty taggings
		poolPartyTaggings = new ArrayList();
		
		try {
			// Open the PoolParty output file
			File poolpartyFile = new File(ppOutputFileName);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(poolpartyFile);
		
			// Get all of the "tags:taggedResource" nodes
			NodeList resourceTags = doc.getElementsByTagName("tags:taggedResource");
			Node correctResourceTag = null;
			for (int x=0; x < resourceTags.getLength(); x++) {
				// Find the one that has an "rdf:resource" value matching documentURN
				Node thisResourceTag = resourceTags.item(x);
				NamedNodeMap attribs = thisResourceTag.getAttributes();
				Node resource = attribs.getNamedItem("rdf:resource");
				if (resource.getNodeValue().equals(documentURN)) {
					correctResourceTag = thisResourceTag;
					break;
				}
			}  // end for each resource tag
						
			// TODO if correctResourceTag is still null there is a problem.
			
			// Get that tag's parent "Description" node.
			Node description = correctResourceTag.getParentNode();
			
			// Get the "tags:associatedTag" nodes from the parent "Description" node.
			List tagResources = new ArrayList();
			NodeList descriptionChildren= description.getChildNodes();
			for (int x=0; x < descriptionChildren.getLength(); x++) {
				Node thisTag = descriptionChildren.item(x);
				
				if (thisTag.getNodeName().equals("tags:associatedTag")) {
					// Get the tagged resource and add it to the list.
					NamedNodeMap attribs = thisTag.getAttributes();
					Node resource = attribs.getNamedItem("rdf:resource");
					tagResources.add(resource.getNodeValue());
				}					
			}  // end for each descriptionChildren
			
			// Get the name of the concept for each tag resource.
			NodeList descNodes = doc.getElementsByTagName("rdf:Description");
			for (int x=0; x < descNodes.getLength(); x++) {
				description = descNodes.item(x);
				
				// get the "rdf:about" attribute.
				NamedNodeMap attribs = description.getAttributes();
				Node about = attribs.getNamedItem("rdf:about");
				
				// If the attribute is not one of the tag resources keep going.
				if (about == null || !tagResources.contains(about.getNodeValue())) 
					continue;

				// Get the child nodes for the description
				NodeList childNodes = description.getChildNodes();
				for (int y=0; y < childNodes.getLength(); y++) {
					Node thisNode = childNodes.item(y);
					
					// From the node list, get the "skos:prefLabel" node
					if (!thisNode.getNodeName().equals("skos:prefLabel"))
						continue;
					
					NodeList prefLabelChild = thisNode.getChildNodes();
					for (int z=0; z < prefLabelChild.getLength(); z++) {
						Node label = prefLabelChild.item(z);
						if (label.getNodeName().equals("#text"))
							poolPartyTaggings.add(label.getNodeValue());
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}  // end loadPoolPartyTaggings()

}
