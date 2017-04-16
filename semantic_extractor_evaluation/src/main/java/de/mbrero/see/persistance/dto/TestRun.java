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


/**
 * Table reprensentation for a test run.
 * @author massi
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
	private String path;
	
    @Column(name="date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
    @Column(name="result")
	private TestRunResults result;

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
	
}
