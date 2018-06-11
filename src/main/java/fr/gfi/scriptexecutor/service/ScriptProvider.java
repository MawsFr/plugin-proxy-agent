/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.service;

import java.util.Map;

import fr.gfi.scriptexecutor.model.ShellScript;

/**
 * This interface has to be implemented by a class that provides a list of executable scripts. <br />
 * TODO : Replace it by a table in a database.
 */
public interface ScriptProvider {
	/**
	 * Initializes default callable scripts.
	 */
	void initScripts();

	/**
	 * Returns the map of executable scripts.
	 */
	Map<String, ShellScript> getScripts();

	/**
	 * Adds an executable script.
	 * 
	 * @param script
	 *            The script to add
	 */
	void addScript(ShellScript script);

	/**
	 * Sets the list of executable scripts.
	 * 
	 * @param scripts
	 *            The list of executable scripts to set.
	 */
	void setScripts(Map<String, ShellScript> scripts);

	/**
	 * Returns the full script folder path.
	 */
	String getScriptsFolder();
}
