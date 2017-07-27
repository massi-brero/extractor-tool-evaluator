package de.mbrero.see.persistance.dto;


import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.mbrero.see.persistance.dto.types.TestRunResults;


/**
 * Table representation for a test run.
 * @author massi.brero@gmail.com
 *
 */
@Entity
@Table(name = "test_run")
public class TestRun {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int id;  
    
    @Column(name = "input_path")
	private String inputPath = "";
    
    @Column(name = "output_path_extractor_result")
	private String outputPathExtractorResult = "";
    
    @Column(name = "output_path_trec_file")
	private String outputPathTRECFile = "";
	
    @Column(name="date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date = null;
	
    @Enumerated(EnumType.STRING)
    @Column(name="result")
	private TestRunResults result = TestRunResults.PENDING;
    
    @Column(name = "parameters")
	private String parameters = "";
    
    @Column(name = "tester")
	private String tester = "";
    
    @Column(name = "duration")
	private long duration = 0;
    
    @Column(name = "system_info", columnDefinition="TEXT")
	private String systemInformation = "";
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String path) {
		this.inputPath = path;
	}

	public String getOutputPathExtractorResult() {
		return outputPathExtractorResult;
	}

	public void setOutputPathExtractorResult(String outPath) {
		this.outputPathExtractorResult = outPath;
	}

	public String getOutputPathTRECFile() {
		return outputPathTRECFile;
	}

	public void setOutputPathTRECFile(String outputPathTRECFile) {
		this.outputPathTRECFile = outputPathTRECFile;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TestRunResults getResult() {
		return result;
	}

	public void setResult(TestRunResults result) {
		this.result = result;
	}

	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the tester
	 */
	public String getTester() {
		return tester;
	}

	/**
	 * @param tester the tester to set
	 */
	public void setTester(String tester) {
		this.tester = tester;
	}

	/**
	 * @return the systemInformation
	 */
	public String getSystemInformation() {
		return systemInformation;
	}

	/**
	 * @param systemInformation the systemInformation to set
	 */
	public void setSystemInformation(String systemInformation) {
		this.systemInformation = systemInformation;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
}
