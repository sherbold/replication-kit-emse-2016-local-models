package de.ugoe.cs.cpdp.versions;

import weka.core.Instances;

/**
 * Data class for software versions. 
 * @author Steffen Herbold
 */
public class SoftwareVersion {

	/**
	 * name of the project
	 */
	private final String project;
	
	/**
	 * version of the project
	 */
	private final String version;

	/**
	 * data of the version
	 */
	private final Instances instances;
	
	/**
	 * Constructor. Creates a new version. 
	 * @param project name of the project
	 * @param version name of the version
	 * @param instances data of the version
	 */
	public SoftwareVersion(String project, String version, Instances instances) {
		this.project = project;
		this.version = version;
		this.instances = instances;
	}
	
	/**
	 * returns the project name
	 * @return project name
	 */
	public String getProject() {
		return project;
	}
	
	/**
	 * returns the name of the version
	 * @return name of the version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * returns the data of the version
	 * @return data
	 */
	public Instances getInstances() {
		return new Instances(instances);
	}
}
