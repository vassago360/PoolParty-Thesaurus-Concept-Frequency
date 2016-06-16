package msdlWriters;

import java.util.Iterator;
import java.util.List;

import main.TranslatorMapping;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class ServiceWriter {

	private OWLOntology ontology;
	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private String baseMSDLuri;
	private String profileURI;
	private String supplierName;
	
	public ServiceWriter(OWLOntology ontology, OWLOntologyManager manager,
			OWLDataFactory factory, String baseMsdlURI, String profileURI, String supplierName) {
		this.ontology = ontology;
		this.manager = manager;
		this.factory = factory;
		this.baseMSDLuri = baseMsdlURI;
		this.profileURI = profileURI;
		this.supplierName = supplierName;
	}  // end constructor
	
	public void writeToOntology(List<TranslatorMapping> process, List<TranslatorMapping> machineTool, 
			List<TranslatorMapping> material, List<TranslatorMapping> property) {
		// Iterate through each process
		Iterator<TranslatorMapping> procItr = process.iterator();
		while (procItr.hasNext()) {
			TranslatorMapping thisProcess = procItr.next();
			
			// Create a service for this process.
			OWLNamedIndividual thisService = factory.getOWLNamedIndividual(IRI.create(profileURI+"#Svc"+thisProcess.getMsdlName()));
			OWLClass serviceClass = factory.getOWLClass(IRI.create(baseMSDLuri+"#MfgService"));
			
			// Add to the service the hasProcess property with the process.
			OWLNamedIndividual thisProcessInd = factory.getOWLNamedIndividual(IRI.create(profileURI+"#Process"+thisProcess.getMsdlName()));
			OWLClass processClass = factory.getOWLClass(IRI.create(baseMSDLuri+"#"+thisProcess.getMsdlName()));
			OWLClassAssertionAxiom processIndividual = factory.getOWLClassAssertionAxiom(processClass, thisProcessInd);
			manager.addAxiom(ontology, processIndividual);
			OWLObjectPropertyExpression hasProcessProp = factory.getOWLObjectProperty(IRI.create(baseMSDLuri+"#hasProcess"));
			OWLObjectPropertyAssertionAxiom hasProcessAxiom = factory.getOWLObjectPropertyAssertionAxiom(hasProcessProp, thisService, thisProcessInd);
			manager.addAxiom(ontology, hasProcessAxiom);
			
			// Add to the service the hasMaterial property for each material.
			Iterator<TranslatorMapping> matItr = material.iterator();
			while (matItr.hasNext()) {
				TranslatorMapping thisMaterial = matItr.next();
				OWLNamedIndividual materialInd = factory.getOWLNamedIndividual(IRI.create(profileURI+"#Mat"+thisMaterial.getMsdlName()));
				OWLClass materialClass = factory.getOWLClass(IRI.create(baseMSDLuri+"#"+thisMaterial.getMsdlName()));
				OWLClassAssertionAxiom materialAxiom = factory.getOWLClassAssertionAxiom(materialClass, materialInd);
				manager.addAxiom(ontology, materialAxiom);
				OWLObjectPropertyExpression hasMaterialProp = factory.getOWLObjectProperty(IRI.create(baseMSDLuri+"#hasMaterial"));
				OWLObjectPropertyAssertionAxiom hasMaterialAxiom = factory.getOWLObjectPropertyAssertionAxiom(hasMaterialProp, thisService, materialInd);
				manager.addAxiom(ontology, hasMaterialAxiom);
			}  // end while matItr
			
			// Add the production volume property (if it exists)
			Iterator<TranslatorMapping> propItr = property.iterator();
			while (propItr.hasNext()) {
				TranslatorMapping thisProperty = propItr.next();
				if (!thisProperty.getMsdlName().equals("hasProductionVolume"))
					continue;
				
				OWLDataPropertyExpression prodVolProp = factory.getOWLDataProperty(IRI.create(baseMSDLuri+"#hasProductionVolume"));
				OWLDataPropertyAssertionAxiom prodVolAxiom = factory.getOWLDataPropertyAssertionAxiom(prodVolProp, thisService, Integer.parseInt(thisProperty.getMsdlAttribute()));
				manager.addAxiom(ontology, prodVolAxiom);
			}  // end while propItr
			
			// Add the tolerance/accuracy property (if it exists)
			propItr = property.iterator();  // TODO better to combine this with the iteration above.
			while (propItr.hasNext()) {
				TranslatorMapping thisProperty = propItr.next();
				if (!thisProperty.getMsdlName().equals("hasAccuracy"))
					continue;
				
				OWLDataPropertyExpression accuracyProp = factory.getOWLDataProperty(IRI.create(baseMSDLuri+"#hasAccuracy"));
				OWLDataPropertyAssertionAxiom accuracyAxiom = factory.getOWLDataPropertyAssertionAxiom(accuracyProp, thisService, 0.001);
				manager.addAxiom(ontology, accuracyAxiom);
			}  // end while propItr
			
			// Add to the service the isEnabledBy property for the correct machineTool.
			// TODO
			
			// Add this service the supplier profile.
			OWLNamedIndividual supplierProfileInd = factory.getOWLNamedIndividual(IRI.create(profileURI+"#SP_"+supplierName));
			OWLObjectPropertyExpression hasServiceProp = factory.getOWLObjectProperty(IRI.create(baseMSDLuri+"#hasService"));
			OWLObjectPropertyAssertionAxiom hasServiceAxiom = factory.getOWLObjectPropertyAssertionAxiom(hasServiceProp, supplierProfileInd, thisService);
			manager.addAxiom(ontology, hasServiceAxiom);
			OWLClassAssertionAxiom serviceIndividual = factory.getOWLClassAssertionAxiom(serviceClass, thisService);
			manager.addAxiom(ontology, serviceIndividual);
			
		}  // end while procItr
	}  // end writeToOntology()

}
