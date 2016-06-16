package main;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JTextField;
import javax.swing.JButton;

import main.WebRetriever.RetrieveType;

public class Translator_v3 {

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
	private JTextField supplierURLTextField = null;
	private JLabel baseMSDLLabel = null;
	private JTextField baseMSDLTextField = null;
	private JLabel outputFileLabel = null;
	private JTextField outputFileTextField = null;
	private JButton outputFileButton = null;
	private JLabel supplierNameLabel = null;
	private JTextField supplierNameTextField = null;
	private JButton createProfileButton = null;
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
			baseMSDLTextField.setText("http://147.26.174.31/ontology/MSDL-Base.owl");
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
					if (outputFileTextField.getText().trim().length() == 0)
						error += "You must provide the directory and filename of the output ontology.\n";
					if (supplierNameTextField.getText().trim().length() == 0)
						error += "You must provide the name of the supplier.\n";

					if (error.trim().length() != 0) {
						// Display the error message.
						JOptionPane.showMessageDialog(null, error, "Cannot perform translation", JOptionPane.ERROR_MESSAGE);		
					} else {
						// Call get web contents
						WebRetriever web = new WebRetriever(RetrieveType.GENERIC);
						String sourceDocumentText = web.getWebPage(supplierURLTextField.getText());
						
						// Call Supplier Profile Creator
						SupplierProfileCreator spCreator = new SupplierProfileCreator(sourceDocumentText, 
								outputFileTextField.getText(), baseMSDLTextField.getText(), 
								supplierNameTextField.getText());
						spCreator.createSupplierProfile();
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Translator_v3 application = new Translator_v3();
				application.getJFrame().setVisible(true);
				application.prepopulate();
			}
		});
	}

	private void prepopulate() {
		// Kludge to only pre-populate fields when testing from Eclipse (and output file already exists)
		File checkFile = new File(translatorOutputFile);
		if (checkFile.exists()) {
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
			jFrame.setSize(537, 300);
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
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridwidth = 3;
			gridBagConstraints14.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints14.gridy = 7;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints13.ipadx = 200;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			gridBagConstraints12.gridy = 1;
			supplierNameLabel = new JLabel();
			supplierNameLabel.setText("Supplier's Name: ");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new Insets(5, 10, 5, 10);
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints10.ipadx = 200;
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints9.gridy = 2;
			outputFileLabel = new JLabel();
			outputFileLabel.setText("Output Ontology File: ");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridy = 4;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints8.ipadx = 200;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.gridy = 4;
			baseMSDLLabel = new JLabel();
			baseMSDLLabel.setText("Base MSDL Ontology URI: ");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.ipadx = 200;
			gridBagConstraints6.ipady = 0;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new Insets(10, 10, 5, 5);
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridy = 0;
			supplierURLLabel = new JLabel();
			supplierURLLabel.setText("Supplier's website URL: ");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(supplierURLLabel, gridBagConstraints5);
			jContentPane.add(getSupplierURLTextField(), gridBagConstraints6);
			jContentPane.add(baseMSDLLabel, gridBagConstraints7);
			jContentPane.add(getBaseMSDLTextField(), gridBagConstraints8);
			jContentPane.add(outputFileLabel, gridBagConstraints9);
			jContentPane.add(getOutputFileTextField(), gridBagConstraints10);
			jContentPane.add(getOutputFileButton(), gridBagConstraints11);
			jContentPane.add(supplierNameLabel, gridBagConstraints12);
			jContentPane.add(getSupplierNameTextField(), gridBagConstraints13);
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
