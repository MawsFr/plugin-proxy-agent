package fr.gfi.scriptexecutor.service.context;

import java.util.Map;

/**
 * Contains the execution context oif the script to execute.
 */
public interface IContext {
	/**
	 * 
	 * @return The id of the script to execute
	 */
	String getScriptId();

	/**
	 * 
	 * @return The parameters to pass to the executed script
	 */
	Map<String, String> getArgs();

}
