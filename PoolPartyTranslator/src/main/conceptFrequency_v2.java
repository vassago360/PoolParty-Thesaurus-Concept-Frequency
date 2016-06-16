package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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

public class conceptFrequency_v2 extends JFrame {

	private JPanel contentPane;
	private JTextField docFileTextField;
	private final Action action = new SwingAction();
	private JTree tree;
	private boolean sumUpFreqCount = false;
	private final Action action_1 = new SwingAction_1();
	private final Action action_2 = new SwingAction_2();
	final JFileChooser fc = new JFileChooser();
	private String fileName = "";
	private final Action action_3 = new SwingAction_3();
	private final Action action_4 = new SwingAction_4();
	private String docString = "";
	private List<String> ppxFoundConcepts =  new ArrayList<String>();

 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					conceptFrequency_v2 frame = new conceptFrequency_v2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public conceptFrequency_v2() {
		setTitle("Pool Party Concept Frequency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 547);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblDocument = new JLabel("Document txt file:");
		lblDocument.setBounds(10, 25, 118, 14);
		contentPane.add(lblDocument);
		
		docFileTextField = new JTextField();
		docFileTextField.setBounds(10, 50, 118, 20);
		contentPane.add(docFileTextField);
		docFileTextField.setColumns(10);
		
		JLabel lblListOfConcepts = new JLabel("List of Concepts and it's frequency:");
		lblListOfConcepts.setBounds(260, 25, 218, 14);
		contentPane.add(lblListOfConcepts);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(260, 61, 428, 426);
		contentPane.add(scrollPane_1);
		
		tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("PPX Concepts") {
				{
				}
			}
		));
		scrollPane_1.setViewportView(tree);
		
		JButton findConceptsDocFreqButton = new JButton("");
		findConceptsDocFreqButton.setAction(action);
		findConceptsDocFreqButton.setBounds(10, 107, 214, 23);
		contentPane.add(findConceptsDocFreqButton);
		
		JRadioButton rdbtnSumUpNarrower = new JRadioButton("Sum up narrower concepts");
		rdbtnSumUpNarrower.setAction(action_4);
		rdbtnSumUpNarrower.setBounds(10, 77, 214, 23);
		contentPane.add(rdbtnSumUpNarrower);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setAction(action_2);
		btnBrowse.setBounds(135, 49, 118, 23);
		contentPane.add(btnBrowse);
	
		JLabel lblProgress = new JLabel("Expect 10-25 minutes to query & compute");
		lblProgress.setBounds(10, 141, 240, 14);
		contentPane.add(lblProgress);
		
	}
	
	public TreeModel getTreeModel() {
		return tree.getModel();
	}
	
	public void setTreeModel(TreeModel model) {
		tree.setModel(model);
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
		if (!(checkIfConceptIsAFoundPpxConcept(concept))) {
			return 0;
		}
		List<String> conceptAndAltAndHiddenPPXLabels = new ArrayList<String>();
		conceptAndAltAndHiddenPPXLabels.add(concept);
		for (String label : s.getAltAndHiddenPPXLabels(concept)) {
			conceptAndAltAndHiddenPPXLabels.add(label);
		}
		int count = 0;
		for (String label : conceptAndAltAndHiddenPPXLabels) {
		     try {
		         // Create matcher on file
		         Pattern pattern = Pattern.compile("\\b" + label + "\\b");
		         Matcher matcher = pattern.matcher(fromFile(fileName));
		         fromFile(fileName);
		     
		         // Find all matches
		         while (matcher.find()) {
		             // Get the matching string
		             //String match = matcher.group();
		        	 count++;
		         }
		     } catch (IOException e) {
		     }					
		}
		if (count != 0) {
			//System.out.println(concept + " " + count);
		}
		return count;
	}

	private boolean checkIfConceptIsAFoundPpxConcept(String concept) {
		for (String ppxFoundConcept : ppxFoundConcepts) {
			if (concept.equals(ppxFoundConcept)) {
				return true;
			}
		}
		return false;
	}
	
  	private MyTreeNode populateTree(SparqlRetriever s, String concept, MyTreeNode treeNode) {
		if (s.getNarrowerPPXConcepts(concept).isEmpty()) {
			//change treeNode name
			treeNode.setUserObject(treeNode.getUserObject() + " " + treeNode.frequency);
			return treeNode;
		} else {
			treeNode.frequency = getConceptFreq(s, concept);
			for (String narrowerConcept : s.getNarrowerPPXConcepts(concept)) {
				if (!narrowerConcept.equals(concept)) {
					MyTreeNode subTree = new MyTreeNode(narrowerConcept);
					subTree.frequency = getConceptFreq(s, narrowerConcept);
					treeNode.add(populateTree(s, narrowerConcept, subTree));
					if (sumUpFreqCount) {
						treeNode.frequency += subTree.frequency;
					}
				}
			}
			//change treeNode name
			treeNode.setUserObject(treeNode.getUserObject() + " " + treeNode.frequency);
			return treeNode;
		}
	}
 	
	private DefaultTreeModel createTreeAndAddChildren() {		
		MyTreeNode treeNode = new MyTreeNode("PPX Concepts");
		SparqlRetriever s = new SparqlRetriever();
		List<String> topConcepts = s.getPPXTopConcepts();
		int i = 1;
		System.out.println(topConcepts);
		for (String topConcept : topConcepts) {
			if (i == 6) {
				System.out.println(i + " " + topConcept);
				treeNode.add(populateTree(s, topConcept, new MyTreeNode(topConcept)));
				//progressTextField.setText(i + "/" + topConcepts.size());
				//System.out.println(progressTextField.getText());								
			}
			i++;
		}
		return new DefaultTreeModel(treeNode);
	}
	
	public class MyTreeNode extends DefaultMutableTreeNode {
		private int frequency = 0;
		MyTreeNode(String s) {
			super(s);
		}
		public boolean doNotAnyChildrenHaveTheName(String s) {
			//System.out.println(this.getChildCount());
			for (int i = 0; i < this.getChildCount(); i++) {
				//System.out.println("this.getChildAt(i).toString(): " + this.getChildAt(i).toString());
				if (this.getChildAt(i).toString().equals(s)) {
					System.out.println("***duplicate found");
					return false;
				} 
			}
			return true;
		}
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Find concept(s) document frequency");
			putValue(SHORT_DESCRIPTION, "Find concept(s) document frequency");
		}
		public void actionPerformed(ActionEvent e) {
			//save ppxTaggings
			SupplierProfileCreator sp = new SupplierProfileCreator(docString);
			ppxFoundConcepts = sp.getPPXconcepts();
			//create tree
			DefaultTreeModel ppxConceptTreeModel = createTreeAndAddChildren();
			setTreeModel(ppxConceptTreeModel);				
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "(Re)create PPX Concept Tree");
			putValue(SHORT_DESCRIPTION, "Query pool party to retrieve the taxonomy.");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Browse");
		}
		public void actionPerformed(ActionEvent e) {
			int returnVal = fc.showOpenDialog(conceptFrequency_v2.this);
			fileName = fc.getSelectedFile().getPath(); //.getName();
			try {
				docString = getDocString(fromFile(fileName));
			} catch (IOException ee) { 
			}			
			docFileTextField.setText(fileName);
		}
	}
	private class SwingAction_3 extends AbstractAction {
		public SwingAction_3() {
			putValue(NAME, "Get Progress Update");
			putValue(SHORT_DESCRIPTION, "Find out how many PPX top concepts have been evaluated");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_4 extends AbstractAction {
		public SwingAction_4() {
			putValue(NAME, "Sum up narrower concepts");
			putValue(SHORT_DESCRIPTION, "Sum up narrower concepts");
		}
		public void actionPerformed(ActionEvent e) {
			if (sumUpFreqCount == true) {
				sumUpFreqCount = false;
			} else {
				sumUpFreqCount = true;
			}	
		}
	}
}