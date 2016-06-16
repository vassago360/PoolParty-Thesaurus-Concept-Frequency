package msdlWriters;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.TranslatorMapping;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class SupplierProfileWriter {

	private String spOutputFile;
	private String supplierName;
	private String baseMsdlURI;

	private List<String> ppTaggings;
	private List<TranslatorMapping> translatorMappings;
	private List<TranslatorMapping> material;
	private List<TranslatorMapping> industry;
	private List<TranslatorMapping> process;
	private List<TranslatorMapping> machineTool;
	private List<TranslatorMapping> property;
	private List<TranslatorMapping> product;
	
	public SupplierProfileWriter(String supplierName, String spOutputFile, String baseMsdlURI,
			List translatorMappings, List ppTaggings) {
		this.supplierName = supplierName.replace(" ", "_");
		this.spOutputFile = spOutputFile;
		this.baseMsdlURI = baseMsdlURI;
		this.translatorMappings = translatorMappings;
		this.ppTaggings = ppTaggings;
	}  //end constructor
	
	public void createProfile() {
		// Create/Declare the variables needed to create the MSDL Profile
		OWLOntologyManager manager = null;
		OWLOntology ontology = null;
		OWLDataFactory factory = null;
		String profileURI = "http://infoneer.txstate.edu/ontology/"+supplierName+".owl";
		try {
			// Create/Open the OWL ontology for the MSDL profile.
			manager = OWLManager.createOWLOntologyManager();
			// Set up the IRIs and map them together.
			IRI ontologyIRI = IRI.create(profileURI);
			String docPathAndFile = spOutputFile;
			IRI documentIRI = IRI.create(new File(docPathAndFile).toURI());
			SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
			manager.addIRIMapper(mapper);
			
			// Create the ontology
			ontology = manager.createOntology(ontologyIRI);
			factory = manager.getOWLDataFactory();
		} catch (OWLException e) {
			e.printStackTrace();
		}
		
		// If any of the major OWL variables are still NULL, something went wrong.
		if (manager == null || ontology == null || factory == null) {
			System.err.println("One of the major OWL variables is still set to null.  Cannot continue.");
			System.exit(1);
		}
		
		// Write the copyright information to the ontology file.
		OWLAnnotation copyright = factory.getOWLAnnotation(
				factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI()), 
				factory.getOWLLiteral("(c) 2010-2012 by Farhad Ameri and the Engineering Informatics Research Group.  "
						+"Licensed under Creative Commons Attribution-NonCommercial license.", "en"));
		manager.applyChange(new AddOntologyAnnotation(ontology, copyright));
		
		// Import MSDL
		OWLOntology msdl = null;
		try {
			msdl = manager.loadOntology(IRI.create(baseMsdlURI));
		}  catch (OWLOntologyCreationException e) {
			System.err.println("An error occurred loading/importing MSDL.");
			e.printStackTrace();
		}
		if (msdl == null) {
			System.err.println("MSDL did not get loaded/imported.  Cannot continue.");
			System.exit(1);
		}
		manager.applyChange(new AddImport(ontology, factory.getOWLImportsDeclaration(IRI.create(baseMsdlURI))));
		
		// Sort the PoolParty taggings into their areas.
		sortPPTaggings();
		
		// Prep work for the new MSDL supplier profile is complete.  Time to start creating the actual profile.
		
		// Create the services
		ServiceWriter serviceWriter = new ServiceWriter(ontology, manager, factory, baseMsdlURI, profileURI, supplierName);
		serviceWriter.writeToOntology(process, machineTool, material, property); 
		
		// Create the actor
		ActorWriter actorWriter = new ActorWriter(ontology, manager, factory, baseMsdlURI, profileURI, supplierName);
		actorWriter.writeToOntology(industry, product, property);
		
		// Create the supplier profile.
		OWLClass supplierProfileClass = factory.getOWLClass(IRI.create(baseMsdlURI+"#SupplierProfile"));
		OWLNamedIndividual supplier = factory.getOWLNamedIndividual(IRI.create(profileURI+"#SP_"+supplierName));
		OWLClassAssertionAxiom spAxiom = factory.getOWLClassAssertionAxiom(supplierProfileClass, supplier);
		manager.addAxiom(ontology, spAxiom);
		
		// Save the ontology file.
		try {
			manager.saveOntology(ontology);
		} catch (OWLOntologyStorageException e) {
			System.err.println("An error occurred saving the ontology.");
			e.printStackTrace();
		}
		
		System.out.println("Done!");
	}  // end createProfile()
	
	private void sortPPTaggings() {
		// Setup the different lists.
		material = new ArrayList<TranslatorMapping>();
		industry = new ArrayList<TranslatorMapping>();
		process = new ArrayList<TranslatorMapping>();
		machineTool = new ArrayList<TranslatorMapping>();
		property = new ArrayList<TranslatorMapping>();
		product = new ArrayList<TranslatorMapping>();
		
		// Iterate through each PoolParty tagged concept
		Iterator ppItr = ppTaggings.iterator();
		while (ppItr.hasNext()) {
			String thisConcept = (String) ppItr.next();
			
			// Find the concept in the translator mapping
			// TODO What if translatorMappings is NULL because I'm getting the translation from PoolParty?
			TranslatorMapping thisMapping = getMapping(thisConcept);
			if (thisMapping == null)
				continue;
			
			// Add mapping to the correct 'bin' based on MSDL type.
			if (thisMapping.getMsdlType().equals("Industry"))
				industry.add(thisMapping);
			else if (thisMapping.getMsdlType().equals("Process"))
				process.add(thisMapping);
			else if (thisMapping.getMsdlType().equals("MachineTool"))
				machineTool.add(thisMapping);
			else if (thisMapping.getMsdlType().equals("Property"))
				property.add(thisMapping);
			else if (thisMapping.getMsdlType().equals("Product"))
				product.add(thisMapping);
			else if (thisMapping.getMsdlType().equals("Material"))
				material.add(thisMapping);
		}  // end while ppItr
	}  // end sortPPTaggings()
	
	/**
	 * For a specified concept, find its translation mapping and return it.
	 * @param concept - Concept to find the translation of
	 * @return TranslatorMapping - the translation mapping.
	 */
	private TranslatorMapping getMapping(String concept) {
		Iterator itr = translatorMappings.iterator();
		while (itr.hasNext()) {
			TranslatorMapping thisMapping = (TranslatorMapping) itr.next();
			if (thisMapping.getPpConceptTag().equals(concept))
				return thisMapping;
		}  // end while itr translatorMappings
		
		return null;
	}  // end getMapping()
}
