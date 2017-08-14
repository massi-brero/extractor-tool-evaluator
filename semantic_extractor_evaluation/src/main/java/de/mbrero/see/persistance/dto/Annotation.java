package de.mbrero.see.persistance.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import types.Ontology;

/**
 * Table representation for annotation entity.
 * This class is meant as a bean to store detailed information about a concept found in a given 
 * text using a specific extractor.
 * 
 * @author massi.brero@gmail.com
 *
 */
@Entity
@Table(name="annotation")
public class Annotation {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;  
    
    @Column(name="count")
	private int count = 0;
    
    @Column(name="extractor")
    private String extractor = "";
    
    @Column(name="document_id")
	private String documentID = null;
	
    @Column(name="concept_id")
	private String conceptId ="";
    
    @Column(name="ontology")
	private String ontology = "";
    
    @Column(name="cui")
	private String cui = "";
	
    @Column(name="matched_chunk")
	private String matchedChunk = "";
    
    @Column(name="preferred_text")
	private String preferredText = "";
	
    @Column(name="test_run_id")
	private int testRunId = 0;
    

	public void incrementCounter() {
		count++;
	}
    
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the extractor
	 */
	public String getExtractor() {
		return extractor;
	}
	/**
	 * @param extractor the extractor to set
	 */
	public void setExtractor(String extractor) {
		this.extractor = extractor;
	}
	/**
	 * @return the document id
	 */
	public String getDocumentID() {
		return documentID;
	}
	/**
	 * @param document id to set
	 */
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	/**
	 * @return the conceptId
	 */
	public String getConceptId() {
		return conceptId;
	}
	/**
	 * @param cuconceptId the conceptId to set
	 */
	public void setConceptId(String conceptId) {
		this.conceptId = conceptId;
	}
	/**
	 * @return the matchedChunk
	 */
	public String getMatchedChunk() {
		return matchedChunk;
	}
	/**
	 * @param matchedChunk the matchedChunk to set
	 */
	public void setMatchedChunk(String matchedChunk) {
		this.matchedChunk = matchedChunk;
	}
	/**
	 * @return the preferredText
	 */
	public String getPreferredText() {
		return preferredText;
	}
	/**
	 * @param preferredText the prefereedText to set
	 */
	public void setPreferredText(String prefereedText) {
		preferredText = prefereedText;
	}
	/**
	 * @return the ontology
	 */
	public String getOntology() {
		return ontology;
	}
	/**
	 * @param ontology the ontology to set
	 */
	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	/**
	 * @return the testRunId
	 */
	public int getTestRunId() {
		return testRunId;
	}
	/**
	 * @param testRunId the testRunId to set
	 */
	public void setTestRunId(int testRunId) {
		this.testRunId = testRunId;
	}

}
