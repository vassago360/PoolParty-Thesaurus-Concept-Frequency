package main;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;

public class SparqlRetriever {

	public List<String> getPPXTopConcepts()  {
		List<String> topConcepts = new ArrayList<String>();
		  String serviceString = "http://infoneer.poolparty.biz/PoolParty/sparql/Processes";
		  String queryString = "PREFIX skos:<http://www.w3.org/2004/02/skos/core#> SELECT distinct ?prefLabel WHERE { ?x skos:topConceptOf ?uri. 	?x skos:prefLabel ?prefLabel.  ?conceptScheme skos:hasTopConcept ?x.	}  ORDER BY ?conceptScheme";
		  QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceString, queryString) ;
		  try {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("prefLabel") ;       // Get a result variable by name.
		      topConcepts.add(x.toString().substring(0, x.toString().length()-3));
		    }
		    return topConcepts;
		  } finally { qexec.close() ; }
	}
	
	public List<String> getNarrowerPPXConcepts(String concept) {
		//System.out.println("Finding narrowerConcepts for " + concept);
		List<String> narrowerConcepts = new ArrayList<String>();
		  String serviceString = "http://infoneer.poolparty.biz/PoolParty/sparql/Processes";
		  String queryString = "PREFIX skos:<http://www.w3.org/2004/02/skos/core#> SELECT distinct ?prefLabel WHERE { ?x skos:prefLabel \"" + concept + "\"@en . ?narrowerURI skos:broader ?x .  ?narrowerURI <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2004/02/skos/core#Concept> .   ?narrowerURI skos:prefLabel ?prefLabel . }";
		  try {
			QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceString, queryString) ;
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("prefLabel") ;       // Get a result variable by name.
		      narrowerConcepts.add(x.toString().substring(0, x.toString().length()-3));
//		      Resource r = soln.getResource("p") ; // Get a result variable - must be a resource
//		      System.out.println(r);
//		      Literal l = soln.getLiteral("o") ;   // Get a result variable - must be a literal
//		      System.out.println(l);
		    }
		    qexec.close() ;
		    return narrowerConcepts;
		  }  catch (Exception exc) {
			  System.out.println(concept);
			  System.out.println("Sparql.getNarrowerPPXConcepts ERROR!");
			  return narrowerConcepts;
			} 
	}
	
	public List<String> getAltAndHiddenPPXLabels(String concept) {
		//System.out.println("Finding narrowerConcepts for " + concept);
		List<String> altAndHiddenPPXLabels = new ArrayList<String>();
		  String serviceString = "http://infoneer.poolparty.biz/PoolParty/sparql/Processes";
		  String queryString = "PREFIX skos:<http://www.w3.org/2004/02/skos/core#> SELECT distinct ?altLabelAndHiddenLabel WHERE { ?x skos:prefLabel  \"" + concept + "\"@en . { ?x skos:altLabel ?altLabelAndHiddenLabel . } UNION { ?x skos:hiddenLabel ?altLabelAndHiddenLabel .}}";
		  try {
			QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceString, queryString) ;
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("altLabelAndHiddenLabel") ;       // Get a result variable by name.
		      altAndHiddenPPXLabels.add(x.toString().substring(0, x.toString().length()-3));
		    }
		    qexec.close() ;
		    //System.out.println("altAndHiddenPPXLabels: " + altAndHiddenPPXLabels);
		    return altAndHiddenPPXLabels;
		  }  catch (Exception exc) {
			  System.out.println("Sparql.getAltAndHiddenPPXLabels ERROR!");
			  return altAndHiddenPPXLabels;
			} 
	}	
	
}

