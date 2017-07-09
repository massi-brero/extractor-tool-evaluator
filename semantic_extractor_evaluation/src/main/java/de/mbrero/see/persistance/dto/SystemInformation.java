package de.mbrero.see.persistance.dto;

import javax.persistence.Entity;

/**
 * A Bean for storing Information about the system where the test run is taking place.
 * 
 * @author massi.brero@gmail.com
 *
 */
@Entity
public class SystemInformation {
	
	/*
	 * Free processors avalaible while running the test.
	 */
	private int availableProcessors = -1;
	
	/*
	 * Free Memory in OS in Bytes while running the test.
	 */
	private long freeMemory = -1;
	/*
	 * Max Memory for JVM in Bytes while running the test.
	 */
	private long maxMemoryForJVM = -1;
	/*
	 * Max Memory for JVM in Bytes while running the test.
	 */
	private long totalMemoryForJVM = -1;
	/**
	 * Used OS
	 */
	private String OS = "";
	/*
	 * JVM Version
	 */
	private String jvmVersion;
	/*
	 * Java Version
	 */
	private String javaVersion;
	/*
	 * Java vendor
	 */
	private String javaVendor;
	
	/**
	 * @return the availableProcessors
	 */
	public int getAvailableProcessors() {
		return availableProcessors;
	}
	/**
	 * @param availableProcessors the availableProcessors to set
	 */
	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}
	/**
	 * @return the freeMemory
	 */
	public long getFreeMemory() {
		return freeMemory;
	}
	/**
	 * @param freeMemory the freeMemory to set
	 */
	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}
	/**
	 * @return the maxMemoryForJVM
	 */
	public long getMaxMemoryForJVM() {
		return maxMemoryForJVM;
	}
	/**
	 * @param maxMemoryForJVM the maxMemoryForJVM to set
	 */
	public void setMaxMemoryForJVM(long maxMemoryForJVM) {
		this.maxMemoryForJVM = maxMemoryForJVM;
	}
	/**
	 * @return the totalMemoryForJVM
	 */
	public long getTotalMemoryForJVM() {
		return totalMemoryForJVM;
	}
	/**
	 * @param totalMemoryForJVM the totalMemoryForJVM to set
	 */
	public void setTotalMemoryForJVM(long totalMemoryForJVM) {
		this.totalMemoryForJVM = totalMemoryForJVM;
	}
	/**
	 * @return the oS
	 */
	public String getOS() {
		return OS;
	}
	/**
	 * @param oS the oS to set
	 */
	public void setOS(String oS) {
		OS = oS;
	}
	/**
	 * @return the jvmVersion
	 */
	public String getJvmVersion() {
		return jvmVersion;
	}
	/**
	 * @param jvmVersion the jvmVersion to set
	 */
	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}
	/**
	 * @return the javaVersion
	 */
	public String getJavaVersion() {
		return javaVersion;
	}
	/**
	 * @param javaVersion the javaVersion to set
	 */
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	/**
	 * @return the javaVendor
	 */
	public String getJavaVendor() {
		return javaVendor;
	}
	/**
	 * @param javaVendor the javaVendor to set
	 */
	public void setJavaVendor(String javaVendor) {
		this.javaVendor = javaVendor;
	}
	
	

}
