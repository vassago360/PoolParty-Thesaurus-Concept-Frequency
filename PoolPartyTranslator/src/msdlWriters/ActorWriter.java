package msdlWriters;

import java.util.Iterator;
import java.util.List;

import main.TranslatorMapping;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class ActorWriter {

	private OWLOntology ontology;
	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private String baseMSDLuri;
	private String profileURI;
	private String supplierName;
	
	public ActorWriter(OWLOntology ontology, OWLOntologyManager manager,
			OWLDataFactory factory, String baseMsdlURI, String profileURI,
			String supplierName) {
		this.ontology = ontology;
		this.manager = manager;
		this.factory = factory;
		this.baseMSDLuri = baseMsdlURI;
		this.profileURI = profileURI;
		this.supplierName = supplierName;
	}

	public void writeToOntology(List<TranslatorMapping> industry,
			List<TranslatorMapping> product, List<TranslatorMapping> property) {
		// Create the supplier individual based on the supplier's name.
		OWLNamedIndividual supplier = factory.getOWLNamedIndividual(IRI.create(profileURI+"#"+supplierName));
		
		// Add any properties relating to the supplier/actor.
		// TODO
		
		// Add industry focus object properties to the supplier
		Iterator<TranslatorMapping> industryItr = industry.iterator();
		while (industryItr.hasNext()) {
			TranslatorMapping thisIndustry = industryItr.next();
			OWLClass industryTypeClass = factory.getOWLClass(IRI.create(baseMSDLuri+"#"+thisIndustry.getMsdlName()));
			OWLNamedIndividual industryTypeInd = factory.getOWLNamedIndividual(IRI.create(profileURI+"#Industry"+thisIndustry.getMsdlName()));
			OWLClassAssertionAxiom industryTypeAxiom = factory.getOWLClassAssertionAxiom(industryTypeClass, industryTypeInd);
			manager.addAxiom(ontology, industryTypeAxiom);
			OWLObjectPropertyExpression industryTypeProp = factory.getOWLObjectProperty(IRI.create(baseMSDLuri+"#hasIndustryFocus"));
			OWLObjectPropertyAssertionAxiom industryTypePropAxiom = factory.getOWLObjectPropertyAssertionAxiom(industryTypeProp, supplier, industryTypeInd);
			manager.addAxiom(ontology, industryTypePropAxiom);
		}  // end while industryItr
		
		// Add product focus object properties to the supplier.
		Iterator<TranslatorMapping> productItr = product.iterator();
		while (productItr.hasNext()) {
			TranslatorMapping thisProduct = productItr.next();
			OWLClass productTypeClass = factory.getOWLClass(IRI.create(baseMSDLuri+"#"+thisProduct.getMsdlName()));
			OWLNamedIndividual productTypeInd = factory.getOWLNamedIndividual(IRI.create(profileURI+"#Product"+thisProduct.getMsdlName()));
			OWLClassAssertionAxiom productTypeAxiom = factory.getOWLClassAssertionAxiom(productTypeClass, productTypeInd);
			manager.addAxiom(ontology, productTypeAxiom);
			OWLObjectPropertyExpression productTypeProp = factory.getOWLObjectProperty(IRI.create(baseMSDLuri+"#hasProductFocus"));
			OWLObjectPropertyAssertionAxiom productTypePropAxiom = factory.getOWLObjectPropertyAssertionAxiom(productTypeProp, supplier, productTypeInd);
			manager.addAxiom(ontology, productTypePropAxiom);
		}  // end while productItr
		
		// Add certification object properties to the supplier.
		// TODO
		
		// Finish writing information to the ontology and connecting to the supplier profile.
		OWLClass supplierClass = factory.getOWLClass(IRI.create(baseMSDLuri+"#Supplier"));
		OWLClassAssertionAxiom supplierIndividual = factory.getOWLClassAssertionAxiom(supplierClass, supplier);
		manager.addAxiom(ontology, supplierIndividual);
		OWLNamedIndividual supplierProfileInd = factory.getOWLNamedIndividual(IRI.create(profileURI+"#SP_"+supplierName));
		OWLObjectPropertyExpression hasActorProp = factory.getOWLObjectProperty(IRI.create(baseMSDLuri+"#hasActor"));
		OWLObjectPropertyAssertionAxiom hasActorAxiom = factory.getOWLObjectPropertyAssertionAxiom(hasActorProp, supplierProfileInd, supplier);
		manager.addAxiom(ontology, hasActorAxiom);
	}

}
