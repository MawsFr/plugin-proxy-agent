package fr.gfi.scriptexecutor.service;

import java.util.Map;

import fr.gfi.scriptexecutor.model.ShellScript;

public interface ScriptProvider {
	/**
	 * Initializes default callable scripts.
	 */
	void initScripts(); // TODO change the manner to init scripts

	Map<String, ShellScript> getScripts();

	/**
	 * Sets the folder to the folder listed in application.properties and adds the
	 * script to the list of executable scripts
	 * 
	 * @param script
	 *            The script to add
	 */
	void addScript(ShellScript script);

	void setScripts(Map<String, ShellScript> scripts);

	String getScriptsFolder();
}
