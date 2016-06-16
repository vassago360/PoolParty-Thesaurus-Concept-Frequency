package main;

import java.util.ArrayList;
import java.util.List;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import main.WebRetriever.RetrieveType;

public class Translator_v2 {

	private String translatorXMLFile = "TranslatorMapping.xml";
	private String translatorOutputFile = "WisconsinSP.owl";  //  @jve:decl-index=0:
	private String supplierURL = "http://www.wisconsinmetalparts.com/cnc-machining-services.html";
	private String supplierName = "Wisconsin Metal Parts";

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null;  //  @jve:decl-index=0:visual-constraint="640,11"
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JLabel appTitleLabel = null;
	private JLabel copyrightLabel = null;
	private JLabel peopleLabel = null;
	private JLabel universityLabel = null;
	private JLabel supplierURLLabel = null;
	private JLabel enterTextLabel = null;
	private JLabel blankLabel1 = null;
	private JLabel blankLabel2 = null;
	private JLabel blankLabel3 = null;
	private JLabel blankLabel4 = null;
	private JLabel blankLabel5 = null;
	private JLabel blankLabel6 = null;
	private JLabel blankLabel7 = null;
	private JLabel blankLabel8 = null;
	private JLabel blankLabel9 = null;
	private JLabel blankLabel10 = null;
	private JLabel blankLabel11 = null;
	private JLabel blankLabel12 = null;
	private JLabel blankLabel13 = null;
	private JTextField supplierURLTextField = null;
	private JLabel baseMSDLLabel = null;
	private JTextField baseMSDLTextField = null;
	private JLabel outputFileLabel = null;
	private JTextField outputFileTextField = null;
	private JButton outputFileButton = null;
	private JLabel supplierNameLabel = null;
	private JTextField supplierNameTextField = null;
	private JButton createProfileButton = null;
	private JLabel translatorXMLLabel = null;
	private JTextField translatorXMLTextField = null;
	private JTextArea supplierTextArea = null;
	private JButton translatorXMLButton = null;
	private JLabel serviceConceptsLabel = null;
	public JTextArea serviceConceptsTextArea = null;
	private JLabel materialConceptsLabel = null;
	public JTextArea materialConceptsTextArea = null;
	private JLabel industryConceptsLabel = null;
	public JTextArea industryConceptsTextArea = null;
	private JLabel processConceptsLabel = null;
	public JTextArea processConceptsTextArea = null;
	private JLabel otherConceptsLabel = null;
	public JTextArea otherConceptsTextArea = null;
	private JLabel correspondingMsdlServiceConceptsLabel = null;
	public JTextArea correspondingMsdlServiceConceptsTextArea = null;
	private JLabel correspondingMsdlMaterialConceptsLabel = null;
	public JTextArea correspondingMsdlMaterialConceptsTextArea = null;
	private JLabel correspondingMsdlIndustryConceptsLabel = null;
	public JTextArea correspondingMsdlIndustryConceptsTextArea = null;
	private JLabel correspondingMsdlProcessConceptsLabel = null;
	public JTextArea correspondingMsdlProcessConceptsTextArea = null;
	
	
	/**
	 * This method initializes supplierURLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSupplierURLTextField() {
		if (supplierURLTextField == null) {
			supplierURLTextField = new JTextField();
		}
		return supplierURLTextField;
	}

	/**
	 * This method initializes baseMSDLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getBaseMSDLTextField() {
		if (baseMSDLTextField == null) {
			baseMSDLTextField = new JTextField();
			baseMSDLTextField.setText("http://infoneer.txstate.edu/ontology/MSDL-Base.owl");
		}
		return baseMSDLTextField;
	}

	/**
	 * This method initializes outputFileTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getOutputFileTextField() {
		if (outputFileTextField == null) {
			outputFileTextField = new JTextField();
		}
		return outputFileTextField;
	}

	/**
	 * This method initializes outputFileButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOutputFileButton() {
		if (outputFileButton == null) {
			outputFileButton = new JButton();
			outputFileButton.setText("Browse");
			outputFileButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					outputFileTextField.setText(browseFileName());
				}
			});
		}
		return outputFileButton;
	}

	/**
	 * This method initializes supplierNameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSupplierNameTextField() {
		if (supplierNameTextField == null) {
			supplierNameTextField = new JTextField();
		}
		return supplierNameTextField;
	}

	/**
	 * This method initializes createProfileButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private String getSourceDocumentText() {
		String sourceDocumentText;
		if (supplierTextArea.getText().equals("")) {
			WebRetriever web = new WebRetriever(RetrieveType.GENERIC);
			sourceDocumentText = web.getWebPage(supplierURLTextField.getText());
		} else {
			sourceDocumentText = supplierTextArea.getText();
		}
		return sourceDocumentText;
	}
	
	private JButton getCreateProfileButton() {
		if (createProfileButton == null) {
			createProfileButton = new JButton();
			createProfileButton.setText("Create Supplier's Profile");
			createProfileButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Validate the user's input.
					String error = "";
					if (supplierURLTextField.getText().trim().length() == 0)
						error += "You must provide a URL of the supplier's website.\n";
					else {
						try {
							URL supplierURL = new URL(supplierURLTextField.getText());
							@SuppressWarnings("unused")
							URLConnection conn = supplierURL.openConnection();
						} catch (MalformedURLException e1) {
							error += "The supplier website URL is not formatted correctly.\n";
						} catch (IOException e1) {
							error += "There was a problem testing the connection to the supplier's website URL.\n"
								+ "\tPlease double-check that it is correct.\n";
						}
					}
					if (baseMSDLTextField.getText().trim().length() == 0)
						error += "You must provide the base URL of the MSDL ontology.\n"
							+ "\t(The default value should be sufficient.)\n";
					if (translatorXMLTextField.getText().trim().length() == 0)
						error += "You must provide the directory and file name of the Translator Mapping file.\n";
					else {
						File testFile = new File(translatorXMLTextField.getText().trim());
						if (!testFile.exists())
							error += "The Translator Mapping file could not be found in the location specified.\n";
					}
					if (outputFileTextField.getText().trim().length() == 0)
						error += "You must provide the directory and filename of the output ontology.\n";
					if (supplierNameTextField.getText().trim().length() == 0)
						error += "You must provide the name of the supplier.\n";

					if (error.trim().length() != 0) {
						// Display the error message.
						JOptionPane.showMessageDialog(null, error, "Cannot perform translation", JOptionPane.ERROR_MESSAGE);		
					} else {
						// Call get web contents
						String sourceDocumentText = getSourceDocumentText();
						//System.out.println(sourceDocumentText);
						
						// Call Supplier Profile Creator
						SupplierProfileCreator spCreator = new SupplierProfileCreator(translatorXMLTextField.getText(),
								sourceDocumentText, outputFileTextField.getText(), baseMSDLTextField.getText(), 
								supplierNameTextField.getText());
						spCreator.createSupplierProfile();
						
						// Update serviceConceptsTextArea, ...
						serviceConceptsTextArea.setText(convertListStringToString(spCreator.ServiceConceptsFound));
						materialConceptsTextArea.setText(convertListStringToString(spCreator.MaterialConceptsFound));
						industryConceptsTextArea.setText(convertListStringToString(spCreator.IndustryConceptsFound));
						processConceptsTextArea.setText(convertListStringToString(spCreator.ProcessConceptsFound));
						otherConceptsTextArea.setText(convertListStringToString(spCreator.OtherConceptsFound));
						
						// Update correspondingMsdlServiceConceptsTextArea, ...
						findAndUpdateMSDLTextArea(spCreator.ServiceConceptsFound, correspondingMsdlServiceConceptsTextArea);
						findAndUpdateMSDLTextArea(spCreator.MaterialConceptsFound, correspondingMsdlMaterialConceptsTextArea);
						findAndUpdateMSDLTextArea(spCreator.IndustryConceptsFound, correspondingMsdlIndustryConceptsTextArea);
						findAndUpdateMSDLTextArea(spCreator.ProcessConceptsFound, correspondingMsdlProcessConceptsTextArea);
						
						//Call SparalRetriever
						//SparqlRetriever sr = new SparqlRetriever();
						//sr.queryPPX();
						
						String successMsg = "The PoolParty information has been translated into\n"
							+"an MSDL supplier profile and saved to "
							+outputFileTextField.getText();
						JOptionPane.showMessageDialog(null, successMsg, "Translation Completed", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return createProfileButton;
	}
	
	private boolean findIfXMLThesaurusStringMatchesAPpxConcept(String thesaurusString, List<String> ppxConcept) {
		for (String concept : ppxConcept) {
			if (concept.equals(thesaurusString)) {
				return true;
			}
		}
		return false;
	}
	
	private void findAndUpdateMSDLTextArea(List<String> ppxConcept, JTextArea correspondingMsdlConceptsTextArea) {
		try {
			// Open the translator mapping file.
			File translatorFile = new File(translatorXMLFile);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(translatorFile);
			List<String> correspondingMsdlConcepts = new ArrayList<String>();
			
			// For each concept in the mapping file.
			NodeList concepts = doc.getElementsByTagName("Concept");
			for (int x = 0; x < concepts.getLength(); x++) {
				Node thisConcept = concepts.item(x);
				NodeList childNodes = thisConcept.getChildNodes();
				
				for (int y=0; y < childNodes.getLength(); y++) {
					Node childNode = childNodes.item(y);
					if (childNode.getNodeName().equalsIgnoreCase("#text"))
						continue;  // ignore text nodes, they aren't used.
					// Get the Thesaurus concept.
					if (childNode.getNodeName().equalsIgnoreCase("Thesaurus")) {
						//String ppxConcept = childNode.getAttributes().item(0).toString().substring(1,childNode.getAttributes().item(0).toString().length()-1);
						NamedNodeMap thesaurusAttribs = childNode.getAttributes();
						Node thesaurusValue = thesaurusAttribs.getNamedItem("value");
						// check to see if the xml thesaurus string matches any ppxConcept 
						if (!(findIfXMLThesaurusStringMatchesAPpxConcept(thesaurusValue.getTextContent(), ppxConcept))) {
							break;
						} 
					}
					// Get MSDL concept.
					if (childNode.getNodeName().equalsIgnoreCase("MSDL")) {
						NodeList msdlChildNodes = childNode.getChildNodes();
						for (int z=0; z < msdlChildNodes.getLength(); z++) {
							Node msdlChild = msdlChildNodes.item(z);
							if (msdlChild.getNodeName().equalsIgnoreCase("name")) {
								NamedNodeMap msdlAttribs = msdlChild.getAttributes();
								Node msdlValue = msdlAttribs.getNamedItem("value");
								correspondingMsdlConcepts.add(msdlValue.getTextContent());							
							}
						}
					}
				}
			}
			// update MSDL Text Area
			correspondingMsdlConceptsTextArea.setText(convertListStringToString(correspondingMsdlConcepts));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}  // end loadTranslatorMappings()
	
	private String convertListStringToString(List<String> listString) {
		String totalString = "";
		for (String individualString : listString) {
			totalString += individualString + ", ";
		}
		if (totalString.length() > 1) {
			totalString = totalString.substring(0, totalString.length()-2);
		}
		return totalString;
	}

	/**
	 * This method initializes translatorXMLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTranslatorXMLTextField() {
		if (translatorXMLTextField == null) {
			translatorXMLTextField = new JTextField();
		}
		return translatorXMLTextField;
	}
	
	private JTextArea getSupplierTextArea() {
		if (supplierTextArea == null) {
			supplierTextArea = new JTextArea(2000, 20);
		}
		supplierTextArea.setLineWrap(true);
		supplierTextArea.setText("");
		return supplierTextArea;
	}
	
	private JTextArea placeServiceConceptsTextArea() {
		if (serviceConceptsTextArea == null) {
			serviceConceptsTextArea = new JTextArea(2000, 20);
		}
		serviceConceptsTextArea.setLineWrap(true);
		serviceConceptsTextArea.setText("");
		return serviceConceptsTextArea;
	}
	
	private JTextArea placeMaterialConceptsTextArea() {
		if (materialConceptsTextArea == null) {
			materialConceptsTextArea = new JTextArea(2000, 20);
		}
		materialConceptsTextArea.setLineWrap(true);
		materialConceptsTextArea.setText("");
		return materialConceptsTextArea;
	}

	private JTextArea placeIndustryConceptsTextArea() {
		if (industryConceptsTextArea == null) {
			industryConceptsTextArea = new JTextArea(2000, 20);
		}
		industryConceptsTextArea.setLineWrap(true);
		industryConceptsTextArea.setText("");
		return industryConceptsTextArea;
	}

	private JTextArea placeProcessConceptsTextArea() {
		if (processConceptsTextArea == null) {
			processConceptsTextArea = new JTextArea(2000, 20);
		}
		processConceptsTextArea.setLineWrap(true);
		processConceptsTextArea.setText("");
		return processConceptsTextArea;
	}

	private JTextArea placeOtherConceptsTextArea() {
		if (otherConceptsTextArea == null) {
			otherConceptsTextArea = new JTextArea(2000, 20);
		}
		otherConceptsTextArea.setLineWrap(true);
		otherConceptsTextArea.setText("");
		return otherConceptsTextArea;
	}

	private JTextArea placeMsdlServiceConceptsTextArea() {
		if (correspondingMsdlServiceConceptsTextArea == null) {
			correspondingMsdlServiceConceptsTextArea = new JTextArea(2000, 20);
		}
		correspondingMsdlServiceConceptsTextArea.setLineWrap(true);
		correspondingMsdlServiceConceptsTextArea.setText("");
		return correspondingMsdlServiceConceptsTextArea;
	}
	
	private JTextArea placeMsdlMaterialConceptsTextArea() {
		if (correspondingMsdlMaterialConceptsTextArea == null) {
			correspondingMsdlMaterialConceptsTextArea = new JTextArea(2000, 20);
		}
		correspondingMsdlMaterialConceptsTextArea.setLineWrap(true);
		correspondingMsdlMaterialConceptsTextArea.setText("");
		return correspondingMsdlMaterialConceptsTextArea;
	}
	
	private JTextArea placeMsdlIndustryConceptsTextArea() {
		if (correspondingMsdlIndustryConceptsTextArea == null) {
			correspondingMsdlIndustryConceptsTextArea = new JTextArea(2000, 20);
		}
		correspondingMsdlIndustryConceptsTextArea.setLineWrap(true);
		correspondingMsdlIndustryConceptsTextArea.setText("");
		return correspondingMsdlIndustryConceptsTextArea;
	}
	
	private JTextArea placeMsdlProcessConceptsTextArea() {
		if (correspondingMsdlProcessConceptsTextArea == null) {
			correspondingMsdlProcessConceptsTextArea = new JTextArea(2000, 20);
		}
		correspondingMsdlProcessConceptsTextArea.setLineWrap(true);
		correspondingMsdlProcessConceptsTextArea.setText("");
		return correspondingMsdlProcessConceptsTextArea;
	}
	
	/**
	 * This method initializes translatorXMLButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTranslatorXMLButton() {
		if (translatorXMLButton == null) {
			translatorXMLButton = new JButton();
			translatorXMLButton.setText("Browse");
			translatorXMLButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					translatorXMLTextField.setText(browseFileName());
				}
			});
		}
		return translatorXMLButton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Translator_v2 application = new Translator_v2();
				application.getJFrame().setVisible(true);
				application.prepopulate();
			}
		});
	}

	private void prepopulate() {
		// Kludge to only pre-populate fields when testing from Eclipse (and output file already exists)
		File checkFile = new File(translatorOutputFile);
		if (checkFile.exists()) {
			translatorXMLTextField.setText(translatorXMLFile);
			outputFileTextField.setText(translatorOutputFile);
			supplierURLTextField.setText(supplierURL);
			supplierNameTextField.setText(supplierName);
		}
	}  // end prepopulate()
	
	/**
	 * This method will present to the user a GUI based file chooser so that they can select the 
	 * file containing the ontology they wish to load.
	 * 
	 * @return File The file selected by the user.
	 */
	private String browseFileName() {
		File file;
		
		// Provide a dialog window to let the user choose a file with the ontology
		JFileChooser fc = new JFileChooser();
		int ret = fc.showOpenDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			// Load the file.
			file = fc.getSelectedFile();
		} else {return null;}  // File wasn't selected or some error.
		
		return file.getAbsolutePath();
	}  // end promptUser()
	
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(900, 760);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("MSDL Supplier Profile Translator");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridy = 5;
			gridBagConstraints24.weightx = 1.0;
			gridBagConstraints24.ipadx = 200;
			gridBagConstraints24.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints24.gridx = 1;
			blankLabel5 = new JLabel();
			blankLabel5.setText("");
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 6;
			gridBagConstraints25.weightx = 1.0;
			gridBagConstraints25.ipadx = 200;
			gridBagConstraints25.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints25.gridx = 1;
			blankLabel6 = new JLabel();
			blankLabel6.setText("or");
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridy = 3;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.ipadx = 200;
			gridBagConstraints22.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints22.gridx = 1;
			blankLabel3 = new JLabel();
			blankLabel3.setText("");
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridy = 4;
			gridBagConstraints23.weightx = 1.0;
			gridBagConstraints23.ipadx = 200;
			gridBagConstraints23.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints23.gridx = 1;
			blankLabel4 = new JLabel();
			blankLabel4.setText("");
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridy = 1;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.ipadx = 200;
			gridBagConstraints20.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints20.gridx = 1;
			blankLabel1 = new JLabel();
			blankLabel1.setText("");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.ipadx = 200;
			gridBagConstraints21.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints21.gridx = 1;
			blankLabel2 = new JLabel();
			blankLabel2.setText("");
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			//gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.fill = GridBagConstraints.BOTH;
			gridBagConstraints19.gridy = 0;
			gridBagConstraints19.weightx = 1.0;
			gridBagConstraints19.ipadx = 200;
			gridBagConstraints19.insets = new Insets(5, 5, -50, 5);
			gridBagConstraints19.gridx = 1;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.anchor = GridBagConstraints.EAST;
			gridBagConstraints18.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints18.gridy = 0;
			enterTextLabel = new JLabel();
			enterTextLabel.setText("Enter Supplier Text: ");			
			
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 2;
			gridBagConstraints17.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints17.gridy = 10;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridy = 10;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.ipadx = 200;
			gridBagConstraints16.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints16.gridx = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.anchor = GridBagConstraints.EAST;
			gridBagConstraints15.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints15.gridy = 10;
			translatorXMLLabel = new JLabel();
			translatorXMLLabel.setText("Translator Mapping File: ");
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridwidth = 3;
			gridBagConstraints14.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints14.gridy = 25;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 8;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints13.ipadx = 200;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			gridBagConstraints12.gridy = 8;
			supplierNameLabel = new JLabel();
			supplierNameLabel.setText("Supplier's Name: ");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints11.gridy = 9;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 9;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints10.ipadx = 200;
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints9.gridy = 9;
			outputFileLabel = new JLabel();
			outputFileLabel.setText("Output Ontology File: ");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridy = 11;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints8.ipadx = 200;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.gridy = 11;
			baseMSDLLabel = new JLabel();
			baseMSDLLabel.setText("Base MSDL Ontology URI: ");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridy = 7;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.ipadx = 200;
			gridBagConstraints6.ipady = 0;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new Insets(10, 10, 5, 5);
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridy = 7;
			supplierURLLabel = new JLabel();
			supplierURLLabel.setText("Supplier's website URL: ");
			
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.fill = GridBagConstraints.BOTH;
			gridBagConstraints27.gridy = 12;
			gridBagConstraints27.weightx = 1.0;
			gridBagConstraints27.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints27.ipadx = 200;
			//gridBagConstraints27.ipady = 0;
			gridBagConstraints27.gridx = 1;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.insets = new Insets(20, 5, 5, 5);
			gridBagConstraints26.anchor = GridBagConstraints.EAST;
			gridBagConstraints26.gridy = 12;
			serviceConceptsLabel = new JLabel();
			serviceConceptsLabel.setText("Service Concepts Found: ");
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridy = 13;
			gridBagConstraints36.weightx = 1.0;
			gridBagConstraints36.ipadx = 200;
			gridBagConstraints36.insets = new Insets(30, 5, 5, 5);
			gridBagConstraints36.gridx = 0;
			blankLabel7 = new JLabel();
			blankLabel7.setText("");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 2;
			gridBagConstraints41.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints41.gridy = 12;
			correspondingMsdlServiceConceptsLabel = new JLabel();
			correspondingMsdlServiceConceptsLabel.setText("Corresponding MSDL Service Concepts: ");
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.fill = GridBagConstraints.BOTH;
			gridBagConstraints42.gridy = 12;
			gridBagConstraints42.weightx = 1.0;
			gridBagConstraints42.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints42.ipadx = 200;
			gridBagConstraints42.gridx = 3;

			
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.fill = GridBagConstraints.BOTH;
			gridBagConstraints28.gridy = 14;
			gridBagConstraints28.weightx = 1.0;
			gridBagConstraints28.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints28.ipadx = 200;
			//gridBagConstraints28.ipady = 0;
			gridBagConstraints28.gridx = 1;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridx = 0;
			gridBagConstraints29.insets = new Insets(20, 5, 5, 5);
			gridBagConstraints29.anchor = GridBagConstraints.EAST;
			gridBagConstraints29.gridy = 14;
			materialConceptsLabel = new JLabel();
			materialConceptsLabel.setText("Material Concepts Found: ");
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.gridy = 15;
			gridBagConstraints37.weightx = 1.0;
			gridBagConstraints37.ipadx = 200;
			gridBagConstraints37.insets = new Insets(30, 5, 5, 5);
			gridBagConstraints37.gridx = 0;
			blankLabel8 = new JLabel();
			blankLabel8.setText("");
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.gridx = 2;
			gridBagConstraints43.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints43.gridy = 14;
			correspondingMsdlMaterialConceptsLabel = new JLabel();
			correspondingMsdlMaterialConceptsLabel.setText("Corresponding MSDL Material Concepts: ");
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.fill = GridBagConstraints.BOTH;
			gridBagConstraints44.gridy = 14;
			gridBagConstraints44.weightx = 1.0;
			gridBagConstraints44.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints44.ipadx = 200;
			gridBagConstraints44.gridx = 3;
			
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = GridBagConstraints.BOTH;
			gridBagConstraints30.gridy = 16;
			gridBagConstraints30.weightx = 1.0;
			gridBagConstraints30.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints30.ipadx = 200;
			//gridBagConstraints30.ipady = 0;
			gridBagConstraints30.gridx = 1;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.insets = new Insets(20,5, 5, 5);
			gridBagConstraints31.anchor = GridBagConstraints.EAST;
			gridBagConstraints31.gridy = 16;
			industryConceptsLabel = new JLabel();
			industryConceptsLabel.setText("Industry Concepts Found: ");
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.gridy = 17;
			gridBagConstraints38.weightx = 1.0;
			gridBagConstraints38.ipadx = 200;
			gridBagConstraints38.insets = new Insets(30, 5, 5, 5);
			gridBagConstraints38.gridx = 0;
			blankLabel9 = new JLabel();
			blankLabel9.setText("");
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridx = 2;
			gridBagConstraints45.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints45.gridy = 16;
			correspondingMsdlIndustryConceptsLabel = new JLabel();
			correspondingMsdlIndustryConceptsLabel.setText("Corresponding MSDL Industry Concepts: ");
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.fill = GridBagConstraints.BOTH;
			gridBagConstraints46.gridy = 16;
			gridBagConstraints46.weightx = 1.0;
			gridBagConstraints46.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints46.ipadx = 200;
			gridBagConstraints46.gridx = 3;
			
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.fill = GridBagConstraints.BOTH;
			gridBagConstraints32.gridy = 18;
			gridBagConstraints32.weightx = 1.0;
			gridBagConstraints32.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints32.ipadx = 200;
			//gridBagConstraints32.ipady = 0;
			gridBagConstraints32.gridx = 1;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = 0;
			gridBagConstraints33.insets = new Insets(20, 5, 5, 5);
			gridBagConstraints33.anchor = GridBagConstraints.EAST;
			gridBagConstraints33.gridy = 18;
			processConceptsLabel = new JLabel();
			processConceptsLabel.setText("Process Concepts Found: ");
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridy = 19;
			gridBagConstraints39.weightx = 1.0;
			gridBagConstraints39.ipadx = 200;
			gridBagConstraints39.insets = new Insets(30, 5, 5, 5);
			gridBagConstraints39.gridx = 0;
			blankLabel10 = new JLabel();
			blankLabel10.setText("");
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.gridx = 2;
			gridBagConstraints47.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints47.gridy = 18;
			correspondingMsdlProcessConceptsLabel = new JLabel();
			correspondingMsdlProcessConceptsLabel.setText("Corresponding MSDL Process Concepts: ");
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.fill = GridBagConstraints.BOTH;
			gridBagConstraints48.gridy = 18;
			gridBagConstraints48.weightx = 1.0;
			gridBagConstraints48.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints48.ipadx = 200;
			gridBagConstraints48.gridx = 3;
			
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.fill = GridBagConstraints.BOTH;
			gridBagConstraints34.gridy = 20;
			gridBagConstraints34.weightx = 1.0;
			gridBagConstraints34.insets = new Insets(5, 5, -20, 5);
			gridBagConstraints34.ipadx = 200;
			//gridBagConstraints34.ipady = 0;
			gridBagConstraints34.gridx = 1;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 0;
			gridBagConstraints35.insets = new Insets(20, 5, 5, 5);
			gridBagConstraints35.anchor = GridBagConstraints.EAST;
			gridBagConstraints35.gridy = 20;
			otherConceptsLabel = new JLabel();
			otherConceptsLabel.setText("Other Concepts Found: ");
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.gridy = 21;
			gridBagConstraints40.weightx = 1.0;
			gridBagConstraints40.ipadx = 200;
			gridBagConstraints40.insets = new Insets(30, 5, 5, 5);
			gridBagConstraints40.gridx = 0;
			blankLabel11 = new JLabel();
			blankLabel11.setText("");
			
			
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			
			jContentPane.add(enterTextLabel, gridBagConstraints18);
			JScrollPane scrollPane = new JScrollPane(getSupplierTextArea());
			jContentPane.add(scrollPane, gridBagConstraints19);
			jContentPane.add(blankLabel1, gridBagConstraints20);
			jContentPane.add(blankLabel2, gridBagConstraints21);
			jContentPane.add(blankLabel3, gridBagConstraints22);
			jContentPane.add(blankLabel4, gridBagConstraints23);
			jContentPane.add(blankLabel5, gridBagConstraints24);
			jContentPane.add(blankLabel6, gridBagConstraints25);
			
			jContentPane.add(supplierURLLabel, gridBagConstraints5);
			jContentPane.add(getSupplierURLTextField(), gridBagConstraints6);
			jContentPane.add(translatorXMLLabel, gridBagConstraints15);
			jContentPane.add(getTranslatorXMLTextField(), gridBagConstraints16);
			//jContentPane.add(getTranslatorXMLButton(), gridBagConstraints17);
			jContentPane.add(baseMSDLLabel, gridBagConstraints7);
			jContentPane.add(getBaseMSDLTextField(), gridBagConstraints8);
			jContentPane.add(outputFileLabel, gridBagConstraints9);
			jContentPane.add(getOutputFileTextField(), gridBagConstraints10);
			//jContentPane.add(getOutputFileButton(), gridBagConstraints11);
			jContentPane.add(supplierNameLabel, gridBagConstraints12);
			jContentPane.add(getSupplierNameTextField(), gridBagConstraints13);
			
			jContentPane.add(serviceConceptsLabel, gridBagConstraints26);
			JScrollPane scrollPane2 = new JScrollPane(placeServiceConceptsTextArea());
			jContentPane.add(scrollPane2, gridBagConstraints27);
			jContentPane.add(blankLabel7, gridBagConstraints36); 
			jContentPane.add(correspondingMsdlServiceConceptsLabel, gridBagConstraints41);
			JScrollPane scrollPane7 = new JScrollPane(placeMsdlServiceConceptsTextArea());
			jContentPane.add(scrollPane7, gridBagConstraints42);
			
			jContentPane.add(materialConceptsLabel, gridBagConstraints29);
			JScrollPane scrollPane3 = new JScrollPane(placeMaterialConceptsTextArea());
			jContentPane.add(scrollPane3, gridBagConstraints28);
			jContentPane.add(blankLabel8, gridBagConstraints37);
			jContentPane.add(correspondingMsdlMaterialConceptsLabel, gridBagConstraints43);
			JScrollPane scrollPane8 = new JScrollPane(placeMsdlMaterialConceptsTextArea());
			jContentPane.add(scrollPane8, gridBagConstraints44);
			
			jContentPane.add(industryConceptsLabel, gridBagConstraints31);
			JScrollPane scrollPane4 = new JScrollPane(placeIndustryConceptsTextArea());
			jContentPane.add(scrollPane4, gridBagConstraints30);
			jContentPane.add(blankLabel9, gridBagConstraints38);
			jContentPane.add(correspondingMsdlIndustryConceptsLabel, gridBagConstraints45);
			JScrollPane scrollPane9 = new JScrollPane(placeMsdlIndustryConceptsTextArea());
			jContentPane.add(scrollPane9, gridBagConstraints46);
			
			jContentPane.add(processConceptsLabel, gridBagConstraints33);
			JScrollPane scrollPane5 = new JScrollPane(placeProcessConceptsTextArea());
			jContentPane.add(scrollPane5, gridBagConstraints32);
			jContentPane.add(blankLabel10, gridBagConstraints39);
			jContentPane.add(correspondingMsdlProcessConceptsLabel, gridBagConstraints47);
			JScrollPane scrollPane10 = new JScrollPane(placeMsdlProcessConceptsTextArea());
			jContentPane.add(scrollPane10, gridBagConstraints48);
			
			jContentPane.add(otherConceptsLabel, gridBagConstraints35);
			JScrollPane scrollPane6 = new JScrollPane(placeOtherConceptsTextArea());
			jContentPane.add(scrollPane6, gridBagConstraints34);
			jContentPane.add(blankLabel11, gridBagConstraints40);
			
			jContentPane.add(getCreateProfileButton(), gridBagConstraints14);
			

		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setSize(new Dimension(333, 222));
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.insets = new Insets(5, 10, 10, 10);
			gridBagConstraints4.gridy = 4;
			universityLabel = new JLabel();
			universityLabel.setText("Texas State University-San Marcos");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 3;
			peopleLabel = new JLabel();
			peopleLabel.setText("Farhad Ameri, Christian McArthur, Colin Urbanovsky");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.insets = new Insets(10, 10, 5, 10);
			gridBagConstraints2.gridy = 2;
			copyrightLabel = new JLabel();
			copyrightLabel.setText("Copyright 2012 - Infoneer Research Lab");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints1.gridy = 0;
			appTitleLabel = new JLabel();
			appTitleLabel.setText("PoolParty to MSDL Translator");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = 0;
			gridBagConstraints.ipady = 0;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints.gridy = 1;
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new GridBagLayout());
			aboutContentPane.add(appTitleLabel, gridBagConstraints1);
			aboutContentPane.add(getAboutVersionLabel(), gridBagConstraints);
			aboutContentPane.add(copyrightLabel, gridBagConstraints2);
			aboutContentPane.add(peopleLabel, gridBagConstraints3);
			aboutContentPane.add(universityLabel, gridBagConstraints4);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 0.00003 (prototype)");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}

}
