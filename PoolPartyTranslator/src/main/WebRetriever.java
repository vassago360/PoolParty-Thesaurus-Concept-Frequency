package main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.util.Arrays;

public class WebRetriever {

	public enum RetrieveType {GENERIC, PPX};
	//private String ppProjectUUID = "d77b2696-2a70-4f87-9562-e9281cb5c5f4";  // UUID of Industry project.
	private String ppProjectUUID = "39beef29-72a0-4f7a-bb92-267d1a34ce63";  // UUID of ManuTerms project.
	private String conceptsToRetrieve = "50000";  // Adjust as needed/desired.
	private String termsToRetrieve = "50000"; // Adjust as needed/desired.
	private RetrieveType activeType;
	private DefaultHttpClient client;
	private List<String> ServiceTopConcepts = Arrays.asList("http://infoneer.poolparty.biz/Processes/383", "http://infoneer.poolparty.biz/Processes/384", "http://infoneer.poolparty.biz/Processes/385", "http://infoneer.poolparty.biz/Processes/386");
	private List<String> MaterialTopConcepts = Arrays.asList("http://infoneer.poolparty.biz/Processes/421", "http://infoneer.poolparty.biz/Processes/422", "http://infoneer.poolparty.biz/Casting/361", "http://infoneer.poolparty.biz/Processes/420");
	private List<String> IndustryTopConcepts = Arrays.asList("http://infoneer.poolparty.biz/Industry/1");
	private List<String> ProcessTopConcepts = Arrays.asList("http://infoneer.poolparty.biz/Processes/4", "http://infoneer.poolparty.biz/Processes/403");
	public List<String> ServiceConceptsFound = new ArrayList<String>();
	public List<String> MaterialConceptsFound = new ArrayList<String>();
	public List<String> IndustryConceptsFound = new ArrayList<String>();
	public List<String> ProcessConceptsFound = new ArrayList<String>();
	public List<String> OtherConceptsFound = new ArrayList<String>();
	public List<String> terms = new ArrayList<String>();
	
  	public WebRetriever(RetrieveType retrieveType) {
		this.activeType = retrieveType;
		client = new DefaultHttpClient();
		// TODO May need to set parameters for the client such as User Agent or timeouts.
	}  // end constructor
	
	public String getWebPage(String url) {
		HttpGet request = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		
		String responseBody;
		try {
			responseBody = client.execute(request, responseHandler);
		} catch (ClientProtocolException e) {
			System.err.println("An HTTP protocol error occurred.");
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			System.err.println("The connection was aborted.");
			e.printStackTrace();
			return "";
		}

		String pageBody = extractPageBody(responseBody);
		
		// DEBUG
		//System.out.println(pageBody);
		//System.exit(0);
		
		return pageBody;
	}  // end getWebPage()
	
	/**
	 * This is a naive method for extracting the text of a web page.  All it really does is
	 * remove any tags (<something>).  It doesn't perform any intelligent processing of the
	 * page and its contents.  To do that, an HTMLParser (http://htmlparser.sourceforge.net/)
	 * will be needed.
	 * 
	 * @param pageContents
	 * @return
	 */
	private String extractPageBody(String pageContents) {
		String body = pageContents.replaceAll("<(.|\n)*?>", " ");
		return body;
	}  // end extractPageBody()
	
	public List<String> queryPPX(String sourceDocumentText) {
		List<String> taggings = new ArrayList<String>();
		String ppxUrl = "http://infoneer.poolparty.biz/extractor/api/extract";
		
		// Set up the web post parameters
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("text", sourceDocumentText));
		postParams.add(new BasicNameValuePair("project", ppProjectUUID));
		postParams.add(new BasicNameValuePair("locale", "en"));
		postParams.add(new BasicNameValuePair("format", "rdfxml"));
		postParams.add(new BasicNameValuePair("countConcepts", conceptsToRetrieve));
		postParams.add(new BasicNameValuePair("countTerms", termsToRetrieve));
		postParams.add(new BasicNameValuePair("retrieveTransitiveBroaderTopConcepts", "true"));
		
		// Prepare the request
		HttpPost request = new HttpPost(ppxUrl);
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(postParams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("An error occurred encoding the post request parameters");
			e.printStackTrace();
			return taggings;
		}
		request.setEntity(entity);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		// Perform the request
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody;
		try {
			responseBody = client.execute(request, responseHandler);
		} catch (ClientProtocolException e) {
			System.err.println("An HTTP protocol error occurred.");
			e.printStackTrace();
			return taggings;
		} catch (IOException e) {
			System.err.println("The connection was aborted.");
			e.printStackTrace();
			return taggings;
		}
		
		taggings = parsePPX(responseBody);
		
		// DEBUG
		//System.out.println(responseBody);
		//System.exit(0);
		/*
		System.out.println("Querying PPX with source document text with length "+sourceDocumentText.length());
		System.out.println("   Number of concepts tagged was "+taggings.size());
		Iterator<String> tagItr = taggings.iterator();
		while (tagItr.hasNext()) 
			System.out.println("\t"+tagItr.next());
		*/
		
		return taggings;
	}  // end queryPPX()
	
	
	private List<String> parsePPX(String ppxResponse) {
		List<String> taggings = new ArrayList<String>();
		
		try {
			// Set up to parse stuff from the resulting XML text.
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			InputStream in = new ByteArrayInputStream(ppxResponse.getBytes());
			Reader reader = new InputStreamReader(in, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			Document doc = docBuilder.parse(is);
			
			// Get all of the 'rdf:Description' tags from the text.
			NodeList descriptionTagList = doc.getElementsByTagName("rdf:Description");
			for (int x=0; x < descriptionTagList.getLength(); x++) {
				System.out.println(descriptionTagList.item(x));
				// For each 'rdf:Description' tag...
				Node descriptionTag = descriptionTagList.item(x);
				
				// Get it's children.
				NodeList childTagList = descriptionTag.getChildNodes();
				List<String> topConcepts = new ArrayList<String>();
				String prefLabel = "";
				for (int y=0; y < childTagList.getLength(); y++) {
					Node childTag = childTagList.item(y);
					// Find the 'skos:prefLabel' tag from the children.
					if (childTag.getNodeName().equals("skos:prefLabel")) {
						// Add the contents of '#text' to the list of taggings
						NodeList prefLabelList = childTag.getChildNodes();
						for (int z=0; z < prefLabelList.getLength(); z++) {
							Node label = prefLabelList.item(z);
							if (label.getNodeName().equals("#text")) {
								prefLabel = label.getNodeValue();
								taggings.add(label.getNodeValue());
								//continue descriptionFor;
							}
						}  // end for each child of prefLabletag
					}
					// Find the 'skos:hasTopConcept' tag from the children.
					if (childTag.getNodeName().equals("skos:hasTopConcept")) {
						String topConcept = childTag.getAttributes().item(0).toString().substring(14,childTag.getAttributes().item(0).toString().length()-1);
						topConcepts.add(topConcept);
					}
					// Find terms and add them to this.terms list
					if (childTag.getNodeName().equals("rdfs:label")) {
						String term = childTag.getTextContent();
						terms.add(term);
					}					
				}  // end for each of descriptionTag's children tags
				addConceptToConceptSchemePool(prefLabel, getConceptSchemeFromTopConcepts(topConcepts));
			}  // end for each descriptionTag
		} catch (ParserConfigurationException e) {
			System.err.println("A parser configuration exception occurred.");
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			System.err.println("An unsupported encoding exception occurred.");
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			System.err.println("A SAX exception occurred.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.err.println("An I/O exception occurred.");
			e.printStackTrace();
			return null;
		}
		
		// DEBUG
		/*
		System.out.println(taggings.size()+" tagged concepts found: ");
		Iterator<String> itr = taggings.iterator();
		while (itr.hasNext()) 
			System.out.println("\t"+itr.next());
		System.exit(0);
		*/
		
		return taggings;
	}  // end parsePPX()

	private String getConceptSchemeFromTopConcepts(List<String> topConcepts) {
		for (String topConcept : topConcepts) {
			for (String ServiceTopConcept : ServiceTopConcepts) {
				if (topConcept.equals(ServiceTopConcept)) {
					return "Service";
				}
			}
			for (String MaterialTopConcept : MaterialTopConcepts) {
				if (topConcept.equals(MaterialTopConcept)) {
					return "Material";
				}
			}
			for (String IndustryTopConcept : IndustryTopConcepts) {
				if (topConcept.equals(IndustryTopConcept)) {
					return "Industry";
				}
			}
			for (String ProcessTopConcept : ProcessTopConcepts) {
				if (topConcept.equals(ProcessTopConcept)) {
					return "Process";
				}
			}
		}
		return "Other";
	}

	private void addConceptToConceptSchemePool(String prefLabel, String conceptScheme) {
		if (conceptScheme.equals("Service")) {
			ServiceConceptsFound.add(prefLabel);
		} else if (conceptScheme.equals("Material")) {
			MaterialConceptsFound.add(prefLabel);
		} else if (conceptScheme.equals("Industry")) {
			IndustryConceptsFound.add(prefLabel);
		} else if (conceptScheme.equals("Process")) {
			ProcessConceptsFound.add(prefLabel);
		} else if (conceptScheme.equals("Other")) {
			OtherConceptsFound.add(prefLabel);
		}
	}

}
