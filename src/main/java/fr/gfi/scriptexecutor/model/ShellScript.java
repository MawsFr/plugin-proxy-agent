package fr.gfi.scriptexecutor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShellScript {
	private static final Logger logger = LoggerFactory.getLogger(ShellScript.class);
	// public static final String SCRIPT_EXTENSION = ".bat";
	public static final String SCRIPT_EXTENSION = ".sh";

	protected String id;

	protected String filename;

	protected boolean enabled;

	// TODO : add rights

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically setted to name.sh
	 * 
	 * @param id
	 *            The name of the script
	 */
	public ShellScript(String id) {
		this(id, id + SCRIPT_EXTENSION);
	}

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically setted to name.sh
	 * 
	 * @param id
	 *            The name of the script
	 * 
	 * @param filename
	 *            The name of the script file to execute
	 */
	public ShellScript(String id, String filename) {
		this.id = id;
		this.filename = filename;
		this.enabled = true;
	}

}
