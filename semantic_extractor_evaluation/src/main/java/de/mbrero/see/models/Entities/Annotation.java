package de.mbrero.see.models.Entities;

import java.io.File;

import types.Ontology;

public class Annotation {
	
	private File sourceText;
	private String cui;
	private String matchedChunk;
	private Ontology ontology;
	private int testRunId;
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
