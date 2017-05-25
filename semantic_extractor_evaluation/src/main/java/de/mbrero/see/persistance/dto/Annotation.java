package de.mbrero.see.persistance.dto;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import types.Ontology;

/**
 * Table reprenentation for article entity.
 * This class is meant as a bean to store detailed information about a concept found in a given 
 * text using a specific extractor.
 * 
 * @author massi
 *
 */
@Entity
@Table(name = "article")
public class Annotation {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int id;  
    
    @Column(name = "test_run_id")
	private int count = 0;
    
    @Column(name = "extractor")
    private String extractor = "";
    
    @Column(name = "source_text")
	private File sourceText = null;
	
    @Column(name = "cui")
	private String cui ="";
	
    @Column(name = "matched_chunk")
	private String matchedChunk = "";
    
    @Column(name = "prefereedText")
	private String preferredText = "";
	
    @Column(name = "ontology")
	private Ontology ontology = null;
	
    @Column(name = "test_run_id")
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
	 * @return the sourceText
	 */
	public File getSourceText() {
		return sourceText;
	}
	/**
	 * @param sourceText the sourceText to set
	 */
	public void setSourceText(File sourceText) {
		this.sourceText = sourceText;
	}
	/**
	 * @return the cui
	 */
	public String getCui() {
		return cui;
	}
	/**
	 * @param cui the cui to set
	 */
	public void setCui(String cui) {
		this.cui = cui;
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
		this.preferredText = prefereedText;
	}
	/**
	 * @return the ontology
	 */
	public Ontology getOntology() {
		return ontology;
	}
	/**
	 * @param ontology the ontology to set
	 */
	public void setOntology(Ontology ontology) {
		this.ontology = ontology;
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
