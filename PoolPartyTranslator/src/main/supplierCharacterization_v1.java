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
import java.awt.Component;
import java.awt.Color; 

public class supplierCharacterization_v1 extends JFrame {

	private JPanel contentPane;
	private JTextField docFileTextField;
	private final Action action = new SwingAction();
	private final Action action_2 = new SwingAction_2();
	final JFileChooser fc = new JFileChooser();
	private String fileName = "";
	private String docString = "";
	private List<String> ppxFoundConcepts =  new ArrayList<String>();
	private JTextArea textArea;
	private final Action action_1 = new SwingAction_1();
	private JTable table;
	private JTextField category1TextField;
	private JTextField category2TextField;
	private JTextField category3TextField;
	private JTextField category4TextField;
	private JTextArea category1TextArea;
	private JTextArea category2TextArea;
	private JTextArea category3TextArea;
	private JTextArea category4TextArea;
	private JLabel lblScore1;
	private JLabel lblScore2;
	private JLabel lblScore3;
	private JLabel lblScore4;
	
	private final Action action_3 = new SwingAction_3();

 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					supplierCharacterization_v1 frame = new supplierCharacterization_v1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public supplierCharacterization_v1() {
		setTitle("Supplier Characterization");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 565);
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
		findConceptsDocFreqButton.setBounds(447, 493, 163, 23);
		contentPane.add(findConceptsDocFreqButton);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setAction(action_2);
		btnBrowse.setBounds(207, 56, 118, 23);
		contentPane.add(btnBrowse);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 131, 315, 340);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JLabel lblOrCopy = new JLabel("Or Copy & Paste");
		lblOrCopy.setBounds(10, 95, 186, 14);
		contentPane.add(lblOrCopy);
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.setAction(action_1);
		btnClear.setBounds(10, 493, 72, 23);
		contentPane.add(btnClear);
		
		JLabel lblCaregory = new JLabel("Category 1:");
		lblCaregory.setBounds(346, 32, 118, 14);
		contentPane.add(lblCaregory);
		
		table = new JTable();
		table.setBackground(Color.BLACK);
		table.setBounds(335, 32, 1, 484);
		contentPane.add(table);
		
		category1TextField = new JTextField();
		category1TextField.setBounds(418, 29, 86, 20);
		contentPane.add(category1TextField);
		category1TextField.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(346, 57, 158, 72);
		contentPane.add(scrollPane_1);
		
		category1TextArea = new JTextArea();
		scrollPane_1.setViewportView(category1TextArea);
		
		JLabel lblScore = new JLabel("Score 1:");
		lblScore.setBounds(538, 76, 51, 14);
		contentPane.add(lblScore);
		
		lblScore1 = new JLabel("0");
		lblScore1.setBounds(594, 76, 46, 14);
		contentPane.add(lblScore1);
		
		JLabel lblCategory = new JLabel("Category 2:");
		lblCategory.setBounds(346, 143, 118, 14);
		contentPane.add(lblCategory);
		
		category2TextField = new JTextField();
		category2TextField.setColumns(10);
		category2TextField.setBounds(418, 140, 86, 20);
		contentPane.add(category2TextField);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(346, 168, 158, 72);
		contentPane.add(scrollPane_2);
		
		category2TextArea = new JTextArea();
		scrollPane_2.setViewportView(category2TextArea);
		
		JLabel lblScore_1 = new JLabel("Score 2:");
		lblScore_1.setBounds(538, 187, 51, 14);
		contentPane.add(lblScore_1);
		
		lblScore2 = new JLabel("0");
		lblScore2.setBounds(594, 187, 46, 14);
		contentPane.add(lblScore2);
		
		JLabel lblCategory_1 = new JLabel("Category 3:");
		lblCategory_1.setBounds(346, 254, 118, 14);
		contentPane.add(lblCategory_1);
		
		category3TextField = new JTextField();
		category3TextField.setColumns(10);
		category3TextField.setBounds(418, 251, 86, 20);
		contentPane.add(category3TextField);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(346, 279, 158, 72);
		contentPane.add(scrollPane_3);
		
		category3TextArea = new JTextArea();
		scrollPane_3.setViewportView(category3TextArea);
		
		JLabel lblScore_2 = new JLabel("Score 3:");
		lblScore_2.setBounds(538, 298, 51, 14);
		contentPane.add(lblScore_2);
		
		lblScore3 = new JLabel("0");
		lblScore3.setBounds(594, 298, 46, 14);
		contentPane.add(lblScore3);
		
		JLabel lblCategory_2 = new JLabel("Category 4:");
		lblCategory_2.setBounds(346, 374, 118, 14);
		contentPane.add(lblCategory_2);
		
		category4TextField = new JTextField();
		category4TextField.setColumns(10);
		category4TextField.setBounds(418, 371, 86, 20);
		contentPane.add(category4TextField);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(346, 399, 158, 72);
		contentPane.add(scrollPane_4);
		
		category4TextArea = new JTextArea();
		scrollPane_4.setViewportView(category4TextArea);
		
		JLabel lblScore_3 = new JLabel("Score 4:");
		lblScore_3.setBounds(538, 418, 51, 14);
		contentPane.add(lblScore_3);
		
		lblScore4 = new JLabel("0");
		lblScore4.setBounds(594, 418, 46, 14);
		contentPane.add(lblScore4);
		
		JButton button = new JButton("Clear");
		button.setAction(action_3);
		button.setBounds(346, 493, 72, 23);
		contentPane.add(button);
		
	}
	
	public CharSequence fromFile(String filename) throws IOException {
		// Converts the contents of a file into a CharSequence
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
	
	public static long gcm(long a, long b) {
	    return b == 0 ? a : gcm(b, a % b);
	}
	
	public static String asFraction(long a, long b) {
	    long gcm = gcm(a, b);
	    return (a / gcm) + "/" + (b / gcm);		
	}
	
	public List<String> breakIntoIndividualConcepts(String categoryTextAreaString) {
		List<String> individualConcepts =  new ArrayList<String>();
		String regexExpression = "(\\b\\B+\\b)";
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexExpression, java.util.regex.Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(categoryTextAreaString);
	     while (matcher.find()) {
	         // Get the matching string
	         String match = matcher.group();
	         individualConcepts.add(match);
	     }
		return individualConcepts;
	}
	
	public boolean contains(List<String> ppxFoundConcepts, String concept) {
		for (String ppxFoundConcept : ppxFoundConcepts) {
			String regexExpression = "^" +concept + "$";
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexExpression, java.util.regex.Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(ppxFoundConcept);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}
	
	public void computeScores() {
		//category 1
		int denominator = 0;
		int numerator = 0;
		for (String concept : breakIntoIndividualConcepts(category1TextArea.getText())) {
			if (contains(ppxFoundConcepts, concept)) {
				denominator += 1;
				numerator += 1;
			} else {
				denominator += 1;
			}
		}
		lblScore1.setText(asFraction(numerator, denominator));
		//category 2
		denominator = 0;
		numerator = 0;
		for (String concept : breakIntoIndividualConcepts(category2TextArea.getText())) {
			if (contains(ppxFoundConcepts, concept)) {
				denominator += 1;
				numerator += 1;
			} else {
				denominator += 1;
			}
		}
		lblScore2.setText(asFraction(numerator, denominator));
		//category 3
		denominator = 0;
		numerator = 0;
		for (String concept : breakIntoIndividualConcepts(category3TextArea.getText())) {
			if (contains(ppxFoundConcepts, concept)) {
				denominator += 1;
				numerator += 1;
			} else {
				denominator += 1;
			}
		}
		lblScore3.setText(asFraction(numerator, denominator));
		//category 4
		denominator = 0;
		numerator = 0;
		for (String concept : breakIntoIndividualConcepts(category4TextArea.getText())) {
			if (contains(ppxFoundConcepts, concept)) {
				denominator += 1;
				numerator += 1;
			} else {
				denominator += 1;
			}
		}
		lblScore4.setText(asFraction(numerator, denominator));
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Compute Scores");
			putValue(SHORT_DESCRIPTION, "Compute Scores");
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
			computeScores();
		}
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Clear");
			putValue(SHORT_DESCRIPTION, "Clear");
		}
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
			docFileTextField.setText("");
		}
	}	
	
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Browse");
		}
		public void actionPerformed(ActionEvent e) {
			fileName = fc.getSelectedFile().getPath(); 
			try {
				docString = getDocString(fromFile(fileName));
			} catch (IOException ee) { 
			}			
			docFileTextField.setText(fileName);
		}
	}

	private class SwingAction_3 extends AbstractAction {
		public SwingAction_3() {
			putValue(NAME, "Clear");
			putValue(SHORT_DESCRIPTION, "Clear");
		}
		public void actionPerformed(ActionEvent e) {
			category1TextField.setText("");
			category2TextField.setText("");
			category3TextField.setText("");
			category4TextField.setText("");
			category1TextArea.setText("");
			category2TextArea.setText("");
			category3TextArea.setText("");
			category4TextArea.setText("");
			lblScore1.setText("0");
			lblScore2.setText("0");
			lblScore3.setText("0");
			lblScore4.setText("0");
		}
	}
}