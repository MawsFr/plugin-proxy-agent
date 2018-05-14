package fr.gfi.scriptexecutor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShellScript {
	private static final Logger logger = LoggerFactory.getLogger(ShellScript.class);
	public static final String SCRIPT_EXTENSION = ".bat";
	// public static final String SCRIPT_EXTENSION = ".sh";

	protected String name;

	protected String scriptName;

	protected boolean enabled;

	protected String folder;

	// TODO : add rights

	public ShellScript(String name) {
		this(name, "", name + SCRIPT_EXTENSION);
	}

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically setted to name.sh
	 * 
	 * @param name
	 *            The name of the script
	 */
	public ShellScript(String name, String folder) {
		this(name, folder, name + SCRIPT_EXTENSION);
	}

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically setted to name.sh
	 * 
	 * @param name
	 *            The name of the script
	 * 
	 * @param scriptName
	 *            The name of the script file to execute
	 */
	public ShellScript(String name, String folder, String scriptName) {
		this.name = name;
		this.scriptName = scriptName;
		this.folder = folder;
		this.enabled = true;
	}

	@Override
	public String toString() {
		return name;
	}

}
