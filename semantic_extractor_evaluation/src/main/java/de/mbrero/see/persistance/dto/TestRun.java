package de.mbrero.see.persistance.dto;


import java.util.Date;

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
import de.mbrero.see.persistance.dto.SystemInformation;;


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
    
    @Column(name = "path")
	private String path = "";
	
    @Column(name="date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date = null;
	
    @Column(name="result")
	private TestRunResults result = TestRunResults.PENDING;
    
    @Column(name = "parameters")
	private String parameters = "";
    
    @Column(name = "tester")
	private String tester = "";
    
    @Column(name = "system_information", columnDefinition="SystemInformation")
	private SystemInformation systemInformation = null;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

    @Enumerated(EnumType.STRING)
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
	public SystemInformation getSystemInformation() {
		return systemInformation;
	}

	/**
	 * @param systemInformation the systemInformation to set
	 */
	public void setSystemInformation(SystemInformation systemInformation) {
		this.systemInformation = systemInformation;
	}
	
}
