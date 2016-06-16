package main;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PMTranslator {

	// Testing variables for auto-populating fields:
	private String translatorMappingFile = "TranslatorMapping.xml";  //  @jve:decl-index=0:
	private String ppOutputFile = "WisconsinMetalPP.xml";
	private String translatorOutputFile = "WisconsinSP.owl";
	private List<String> ppDocumentList;
	private String selectedDocument;
	
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
	private JDialog aboutDialog = null;  //  @jve:decl-index=0:visual-constraint="646,13"
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JLabel appTitleLabel = null;
	private JLabel copyrightLabel = null;
	private JLabel universityLabel = null;
	private JLabel peopleLabel = null;
	private JLabel mappingXMLFileLabel = null;
	private JLabel ppExportXMLFileLabel = null;
	private JLabel baseMSDLURLLabel = null;
	private JLabel outputFileLabel = null;
	private JLabel supplierNameLabel = null;
	private JButton createSPButton = null;
	private JTextField mappingXMLTextField = null;
	private JButton mappingXMLBrowseButton = null;
	private JTextField ppExportXMLTextField = null;
	private JButton ppExportXMLBrowseButton = null;
	private JTextField baseMSDLURLTextField = null;
	private JTextField outputFileTextField = null;
	private JButton outputFileBrowseButton = null;
	private JTextField supplierNameTextField = null;
	private JFrame documentListFrame = null;  //  @jve:decl-index=0:visual-constraint="598,308"
	private JPanel documentListPanel = null;
	private JButton selectDocumentButton = null;
	private JScrollPane documentListScrollPane = null;
	private JList documentList = null;
	/**
	 * This method initializes createSPButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCreateSPButton() {
		if (createSPButton == null) {
			createSPButton = new JButton();
			createSPButton.setText("Create Supplier Profile");
			createSPButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String error = "";
					if (mappingXMLTextField.getText().trim().length() == 0) 
						error += "You must provide the location of the translation mapping XML file.\n";
					else {
						File testFile = new File(mappingXMLTextField.getText());
						if (!testFile.exists())
							error += "The translation mapping XML file could not be found at the location specified.\n";
					}
					if (ppExportXMLTextField.getText().trim().length() == 0)
						error += "You must provide the location of the PoolParty exported XML file.\n";
					else {
						File testFile = new File(ppExportXMLTextField.getText());
						if (!testFile.exists())
							error += "The PoolParty exported XML file could not be found at the location specified.\n";
					}
					if (baseMSDLURLTextField.getText().trim().length() == 0)
						error += "You must provide the base URI of the MSDL ontology.\n";
					if (outputFileTextField.getText().trim().length() == 0)
						error += "You must provide the location of where to save the output file.\n";
					
					if (error.trim().length() != 0) {
						// Display the error message.
						JOptionPane.showMessageDialog(null, error, "Cannot perform translation", JOptionPane.ERROR_MESSAGE);
					} else {
						// Prompt the user for the supplier document to translate.
						PoolPartyReader ppReader = new PoolPartyReader(ppExportXMLTextField.getText());
						ppDocumentList = ppReader.getDocumentList();
						getDocumentListFrame().setVisible(true);
					}
				}
			});
		}
		return createSPButton;
	}

	/**
	 * This method initializes mappingXMLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMappingXMLTextField() {
		if (mappingXMLTextField == null) {
			mappingXMLTextField = new JTextField();
			mappingXMLTextField.setColumns(0);
		}
		return mappingXMLTextField;
	}

	/**
	 * This method initializes mappingXMLBrowseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMappingXMLBrowseButton() {
		if (mappingXMLBrowseButton == null) {
			mappingXMLBrowseButton = new JButton();
			mappingXMLBrowseButton.setText("Browse");
			mappingXMLBrowseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					mappingXMLTextField.setText(browseFileName());
				}
			});
		}
		return mappingXMLBrowseButton;
	}

	/**
	 * This method initializes ppExportXMLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPpExportXMLTextField() {
		if (ppExportXMLTextField == null) {
			ppExportXMLTextField = new JTextField();
			ppExportXMLTextField.setColumns(0);
		}
		return ppExportXMLTextField;
	}

	/**
	 * This method initializes ppExportXMLBrowseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPpExportXMLBrowseButton() {
		if (ppExportXMLBrowseButton == null) {
			ppExportXMLBrowseButton = new JButton();
			ppExportXMLBrowseButton.setText("Browse");
			ppExportXMLBrowseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ppExportXMLTextField.setText(browseFileName());
				}
			});
		}
		return ppExportXMLBrowseButton;
	}

	/**
	 * This method initializes baseMSDLURLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getBaseMSDLURLTextField() {
		if (baseMSDLURLTextField == null) {
			baseMSDLURLTextField = new JTextField();
			baseMSDLURLTextField.setText("http://147.26.174.31/ontology/MSDL-Base.owl");
		}
		return baseMSDLURLTextField;
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
	 * This method initializes outputFileBrowseButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOutputFileBrowseButton() {
		if (outputFileBrowseButton == null) {
			outputFileBrowseButton = new JButton();
			outputFileBrowseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					outputFileTextField.setText(browseFileName());
				}
			});
			outputFileBrowseButton.setText("Browse");
		}
		return outputFileBrowseButton;
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
	 * This method initializes documentListFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getDocumentListFrame() {
		if (documentListFrame == null) {
			documentListFrame = new JFrame();
			documentListFrame.setSize(new Dimension(442, 405));
			documentListFrame.setTitle("Select Document to Translate");
			documentListFrame.setContentPane(getDocumentListPanel());
		}
		return documentListFrame;
	}

	/**
	 * This method initializes documentListPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDocumentListPanel() {
		if (documentListPanel == null) {
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.BOTH;
			gridBagConstraints20.gridy = 0;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.weighty = 1.0;
			gridBagConstraints20.insets = new Insets(15, 15, 5, 15);
			gridBagConstraints20.gridx = 0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints19.gridy = 1;
			documentListPanel = new JPanel();
			documentListPanel.setLayout(new GridBagLayout());
			documentListPanel.add(getSelectDocumentButton(), gridBagConstraints19);
			documentListPanel.add(getDocumentListScrollPane(), gridBagConstraints20);
		}
		return documentListPanel;
	}

	/**
	 * This method initializes selectDocumentButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSelectDocumentButton() {
		if (selectDocumentButton == null) {
			selectDocumentButton = new JButton();
			selectDocumentButton.setText("Translate Document");
			selectDocumentButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectedDocument = (String) documentList.getSelectedValue();
					getDocumentListFrame().setVisible(false);
					boolean success = createSupplierProfile();
					if (success) {
						String successMsg = "The PoolParty information has been translated into\n"
							+"an MSDL supplier profile and saved to "
							+outputFileTextField.getText();
						JOptionPane.showMessageDialog(null, successMsg, "Translation Completed", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return selectDocumentButton;
	}

	/**
	 * This method initializes documentListScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDocumentListScrollPane() {
		if (documentListScrollPane == null) {
			documentListScrollPane = new JScrollPane();
			documentListScrollPane.setViewportView(getDocumentList());
		}
		return documentListScrollPane;
	}

	/**
	 * This method initializes documentList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getDocumentList() {
		if (documentList == null) {
			String[] documents = new String[ppDocumentList.size()];
			Iterator<String> docItr = ppDocumentList.iterator();
			int count = 0;
			while (docItr.hasNext()) {
				String thisDoc = docItr.next();
				int idx = thisDoc.indexOf("#*#");
				documents[count++] = thisDoc.substring(0, idx);
			}
			documentList = new JList(documents);
			documentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return documentList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PMTranslator application = new PMTranslator();
				application.getJFrame().setVisible(true);
				application.prepopulate();			
			}
		});
	}
	
	private void prepopulate() {
		// Check for existance of files, and if exists, pre-populate fields.
		File checkFile = new File(translatorMappingFile);
		if (checkFile.exists())
			mappingXMLTextField.setText(translatorMappingFile);
		checkFile = new File(ppOutputFile);
		if (checkFile.exists()) {
			ppExportXMLTextField.setText(ppOutputFile);
			// Kludge to only pre-populate this field when other fields populated
			outputFileTextField.setText(translatorOutputFile);
			supplierNameTextField.setText("Wisconsin Metal Parts");
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
	
	private boolean createSupplierProfile() {
		if (selectedDocument == null || selectedDocument.trim().length() == 0) {
			String error = "You must select a document to translate from the list.";
			JOptionPane.showMessageDialog(null, error, "No document selected", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		
		// Iterate through the document list and find the selected document.
		String documentURN = "";
		Iterator<String> docItr = ppDocumentList.iterator();
		while (docItr.hasNext()) {
			String thisDoc = docItr.next();
			if (thisDoc.startsWith(selectedDocument)) {
				int idx = thisDoc.indexOf("#*#")+3;
				documentURN = thisDoc.substring(idx);
				break;
			}
		}  // end while docItr
		
		// Create the profile.
		SupplierProfileCreator spCreator = new SupplierProfileCreator(mappingXMLTextField.getText(),
					ppExportXMLTextField.getText(), outputFileTextField.getText(), 
					baseMSDLURLTextField.getText(), supplierNameTextField.getText(), documentURN);
		spCreator.createSupplierProfile();
		return true;
	}  // end createSupplierProfile()

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
			jFrame.setSize(543, 332);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("PoolParty-to-MSDL Translator");
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
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.gridy = 4;
			gridBagConstraints18.weightx = 0.0;
			gridBagConstraints18.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints18.ipadx = 200;
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 3;
			gridBagConstraints17.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints17.gridy = 3;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridy = 3;
			gridBagConstraints16.weightx = 0.0;
			gridBagConstraints16.ipadx = 200;
			gridBagConstraints16.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints16.gridx = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.weightx = 0.0;
			gridBagConstraints15.ipadx = 200;
			gridBagConstraints15.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 3;
			gridBagConstraints14.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints14.gridy = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.weightx = 0.0;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints13.ipadx = 200;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 3;
			gridBagConstraints12.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints12.gridy = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 0.0;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.ipadx = 200;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridwidth = 3;
			gridBagConstraints10.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints10.gridy = 5;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints9.gridy = 4;
			supplierNameLabel = new JLabel();
			supplierNameLabel.setText("Supplier's Name: ");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints8.gridy = 3;
			outputFileLabel = new JLabel();
			outputFileLabel.setText("Output Ontology File:");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.gridy = 2;
			baseMSDLURLLabel = new JLabel();
			baseMSDLURLLabel.setText("Base MSDL Ontology URL: ");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridy = 1;
			ppExportXMLFileLabel = new JLabel();
			ppExportXMLFileLabel.setText("PoolParty Exported XML File: ");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridy = 0;
			mappingXMLFileLabel = new JLabel();
			mappingXMLFileLabel.setText("Translator Mapping File: ");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(mappingXMLFileLabel, gridBagConstraints5);
			jContentPane.add(getMappingXMLTextField(), gridBagConstraints11);
			jContentPane.add(getMappingXMLBrowseButton(), gridBagConstraints12);
			jContentPane.add(ppExportXMLFileLabel, gridBagConstraints6);
			jContentPane.add(getPpExportXMLTextField(), gridBagConstraints13);
			jContentPane.add(getPpExportXMLBrowseButton(), gridBagConstraints14);
			jContentPane.add(baseMSDLURLLabel, gridBagConstraints7);
			jContentPane.add(getBaseMSDLURLTextField(), gridBagConstraints15);
			jContentPane.add(outputFileLabel, gridBagConstraints8);
			jContentPane.add(getOutputFileTextField(), gridBagConstraints16);
			jContentPane.add(getOutputFileBrowseButton(), gridBagConstraints17);
			jContentPane.add(supplierNameLabel, gridBagConstraints9);
			jContentPane.add(getSupplierNameTextField(), gridBagConstraints18);
			jContentPane.add(getCreateSPButton(), gridBagConstraints10);
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
			aboutDialog.setSize(new Dimension(335, 199));
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
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.gridy = 3;
			peopleLabel = new JLabel();
			peopleLabel.setText("Farhad Ameri, Christian McArthur, Colin Urbanovsky");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 4;
			universityLabel = new JLabel();
			universityLabel.setText("Texas State University-San Marcos");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
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
			gridBagConstraints.ipadx = 51;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints.gridy = 1;
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new GridBagLayout());
			aboutContentPane.add(appTitleLabel, gridBagConstraints1);
			aboutContentPane.add(getAboutVersionLabel(), gridBagConstraints);
			aboutContentPane.add(copyrightLabel, gridBagConstraints2);
			aboutContentPane.add(peopleLabel, gridBagConstraints4);
			aboutContentPane.add(universityLabel, gridBagConstraints3);
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
			aboutVersionLabel.setText("Version 0.00001 (prototype)");
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
