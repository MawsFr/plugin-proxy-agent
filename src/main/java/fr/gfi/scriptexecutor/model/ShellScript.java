/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * An executable shell script.
 *
 */
@Getter
@Setter
public class ShellScript {
	public static final String SCRIPT_EXTENSION = ".sh";

	/**
	 * Id of script (name of the script without sh)
	 */
	protected String id;

	/**
	 * The file name (by default <id>.sh)
	 */
	protected String filename;

	// TODO : add rights and an attribute enabled that tells if the script is executable

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically set to name.sh
	 * 
	 * @param id
	 *            The name of the script
	 */
	public ShellScript(final String id) {
		this(id, id + SCRIPT_EXTENSION);
	}

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically set to name.sh
	 * 
	 * @param id
	 *            The name of the script
	 * 
	 * @param filename
	 *            The name of the script file to execute
	 */
	public ShellScript(final String id, final String filename) {
		this.id = id;
		this.filename = filename;
	}

}
