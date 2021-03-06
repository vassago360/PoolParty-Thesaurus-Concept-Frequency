package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.regex.*;

import javax.swing.*;

import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.JRadioButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.io.File;
import java.util.Date;
import jxl.*;
import jxl.write.*; 

public class conceptFrequency_v3 extends JFrame {

	private JPanel contentPane;
	private JTextField docFileTextField;
	private final Action action = new SwingAction();
	private boolean sumUpFreqCount = false;
	private final Action action_2 = new SwingAction_2();
	final JFileChooser fc = new JFileChooser();
	private String fileName = "";
	private String fileShortName = "";
	private String docString = "";
	private List<String> ppxFoundConcepts =  new ArrayList<String>();
	private List<String> ppxConceptsFreq = new ArrayList<String>();
	private JTextArea textArea;
	private String outputFileName = "";
	private JTextField textTitle;
	private JLabel lblTotalNumExtractedConcepts;
	private final Action action_1 = new SwingAction_1();

 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					conceptFrequency_v3 frame = new conceptFrequency_v3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public conceptFrequency_v3() {
		setTitle("Pool Party Concept Frequency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 500);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblDocument = new JLabel("Document txt file:");
		lblDocument.setBounds(10, 32, 118, 14);
		contentPane.add(lblDocument);
		
		docFileTextField = new JTextField();
		docFileTextField.setBounds(10, 57, 186, 20);
		contentPane.add(docFileTextField);
		docFileTextField.setColumns(10);
		
		JButton findConceptsDocFreqButton = new JButton("");
		findConceptsDocFreqButton.setAction(action);
		findConceptsDocFreqButton.setBounds(10, 428, 339, 23);
		contentPane.add(findConceptsDocFreqButton);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setAction(action_2);
		btnBrowse.setBounds(207, 56, 118, 23);
		contentPane.add(btnBrowse);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 180, 339, 207);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JLabel lblTextTitle = new JLabel("Text Title:");
		lblTextTitle.setBounds(10, 152, 58, 14);
		contentPane.add(lblTextTitle);
		
		textTitle = new JTextField();
		textTitle.setBounds(78, 149, 129, 20);
		contentPane.add(textTitle);
		textTitle.setColumns(10);
		
		JLabel lblOrCopy = new JLabel("Or Copy & Paste");
		lblOrCopy.setBounds(10, 106, 186, 14);
		contentPane.add(lblOrCopy);
		
		lblTotalNumExtractedConcepts = new JLabel("Total number of extracted concepts:  0");
		lblTotalNumExtractedConcepts.setBounds(10, 398, 238, 14);
		contentPane.add(lblTotalNumExtractedConcepts);
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.setAction(action_1);
		btnClear.setBounds(266, 394, 83, 23);
		contentPane.add(btnClear);
		
	}
	
    // Converts the contents of a file into a CharSequence
	public CharSequence fromFile(String filename) throws IOException {
		FileInputStream input = new FileInputStream(filename);
		FileChannel channel = input.getChannel();
   
		// Create a read-only CharBuffer on the file
		ByteBuffer bbuf = channel.map(FileChannel.MapMode.READ_ONLY, 0, (int)channel.size());
		CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
		input.close();
		return cbuf;
   }
	
	public String getDocString(CharSequence cbuf) {
		return cbuf.toString();
	}
	
	private int getConceptFreq(SparqlRetriever s, String concept) {
		String regexExpression = "(\\b" + concept + "\\b)";
		for (String label : s.getAltAndHiddenPPXLabels(concept)) {
			regexExpression += "|(\\b" + label + "\\b)";
		}
		int count = 0;
		try {
		     // Create matcher on file
			//Pattern pattern = Pattern.compile("[\\b\\B]" +"Cutting");
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexExpression, java.util.regex.Pattern.CASE_INSENSITIVE);
			Matcher matcher;
			if (textArea.getText().equals("")) {
				matcher = pattern.matcher(docString);	
			} else {
				matcher = pattern.matcher(textArea.getText());	
			}
		     //Matcher matcher = pattern.matcher(fromFile(fileName));		 
		     // Find all matches
		     while (matcher.find()) {
		         // Get the matching string
		         //String match = matcher.group();
		         //System.out.println(match);
		    	 count++;
		     }
		 } catch (Exception e) {
		}					
		//if (true) {
		//	System.out.println("-----" + concept + " " + count);
		//}
		return count;
	}

	private List<String> getConceptsFreq(List<String> ppxFoundConcepts) {
		List<String> ppxConceptsFreq = new ArrayList<String>(); 
		SparqlRetriever s = new SparqlRetriever();
		for (String concept : ppxFoundConcepts) {
			//System.out.println("got freq: " + concept + getConceptFreq(s, concept));
			ppxConceptsFreq.add(getConceptFreq(s, concept) + "");
		}
		return ppxConceptsFreq;		
	}

	
	private void saveXMLFile() {
		try {
			if (textArea.getText().equals("")) {
				outputFileName = "conceptFreq-" + fileShortName + ".xls";
			} else {
				outputFileName = "conceptFreq-" + textTitle.getText() + ".xls";	
			}
			WritableWorkbook workbook = Workbook.createWorkbook(new File(outputFileName)); 
			WritableSheet sheet = workbook.createSheet("First Sheet", 0); 
			int count = 0;
			for (String ppxFoundConcept : ppxFoundConcepts) {
				Label label = new Label(0, count, ppxFoundConcept);
				sheet.addCell(label); 
				count++;
			}
			count = 0;
			for (String ppxConceptFreq : ppxConceptsFreq) {
				Label label = new Label(1, count, ppxConceptFreq);
				sheet.addCell(label); 
				count++;
			}
			// All sheets and cells added. Now write out the workbook
			workbook.write();
			workbook.close(); 
		} catch (Exception ee) { 
		}		
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Extract and import to Excel");
			putValue(SHORT_DESCRIPTION, "Extract and import to Excel");
		}
		public void actionPerformed(ActionEvent e) {
			//get ppxTaggings
			SupplierProfileCreator sp;
			if (textArea.getText().equals("")) {
	            sp = new SupplierProfileCreator(docString);
	            ppxFoundConcepts = sp.getPPXconcepts();
			} else {
	            sp = new SupplierProfileCreator(textArea.getText());
	            ppxFoundConcepts = sp.getPPXconcepts();
			}
			//compute frequency for all ppxFoundConcepts
			ppxConceptsFreq = getConceptsFreq(ppxFoundConcepts);
			//save to xml file  
			saveXMLFile();
			//update lblTotalNumExtractedConcepts
			lblTotalNumExtractedConcepts.setText("Total number of extracted concepts:  " + ppxFoundConcepts.size());
			//produce output confirmation screen
			String successMsg = "Export done.  Output file: " + outputFileName;
			JOptionPane.showMessageDialog(null, successMsg, "Complete", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Browse");
		}
		public void actionPerformed(ActionEvent e) {
			int returnVal = fc.showOpenDialog(conceptFrequency_v3.this);
			fileName = fc.getSelectedFile().getPath(); 
			fileShortName = fc.getSelectedFile().getName().substring(0, fc.getSelectedFile().getName().length()-4);
			try {
				docString = getDocString(fromFile(fileName));
			} catch (IOException ee) { 
			}			
			docFileTextField.setText(fileName);
		}
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Clear");
			putValue(SHORT_DESCRIPTION, "Clear");
		}
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
			lblTotalNumExtractedConcepts.setText("Total number of extracted concepts:  0");
		}
	}
}