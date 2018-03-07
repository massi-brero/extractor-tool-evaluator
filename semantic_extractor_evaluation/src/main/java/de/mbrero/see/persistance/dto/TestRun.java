package de.mbrero.see.persistance.dto;


import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
	
    @Column(name="result")
	private String result = "PENDING";
    
    @Column(name = "parameters")
	private String parameters = "";
    
    @Column(name = "tester")
	private String tester = "";
    
    @Column(name = "duration")
	private long duration = 0;
    
    @Column(name = "system_info", columnDefinition="TEXT")
	private String systemInformation = "";
    
    @Column(name = "extractor")
	private String extractor = "";
    
    /**
     * 
     * @return int
     */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param int id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return String
	 */
	public String getInputPath() {
		return inputPath;
	}

	/**
	 * 
	 * @param String path
	 */
	public void setInputPath(String path) {
		this.inputPath = path;
	}

	/**
	 * 
	 * @return String
	 */
	public String getOutputPathExtractorResult() {
		return outputPathExtractorResult;
	}

	/**
	 * 
	 * @param String outPath
	 */
	public void setOutputPathExtractorResult(String outPath) {
		this.outputPathExtractorResult = outPath;
	}

	/**
	 * 
	 * @return String
	 */
	public String getOutputPathTRECFile() {
		return outputPathTRECFile;
	}

	/**
	 * 
	 * @param String outputPathTRECFile
	 */
	public void setOutputPathTRECFile(String outputPathTRECFile) {
		this.outputPathTRECFile = outputPathTRECFile;
	}

	/**
	 * 
	 * @return {@link Date}
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @param {@link Date} date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @return String
	 */
	public String getResult() {
		return result;
	}

	/**
	 * String
	 * @param result
	 */
	public void setResult(String result) {
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

	/**
	 * 
	 * @return {@link Duration}
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * 
	 * @param {@link Duration} duration
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * 
	 * @return String
	 */
	public String getExtractor() {
		return extractor;
	}

	/**
	 * String
	 * @param extractor
	 */
	public void setExtractor(String extractor) {
		this.extractor = extractor;
	}
	
}
