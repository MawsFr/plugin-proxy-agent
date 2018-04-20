package fr.gfi.scriptexecutor.service.scripts;

import java.util.List;

import fr.gfi.scriptexecutor.service.context.IContext;

/**
 * This class represents a script to execute
 * 
 * @author frus68783
 *
 */
public interface ShellScript {
	/**
	 * Executes this script with the args from the front
	 * 
	 * @param context
	 *            The context containing the args of the script
	 * @return An object containing the message in JSON format : {message, exitcode}
	 */
	ExecutionResult execute(IContext context);

	/**
	 * Gets an ordered list of args to know in which order to pass the script
	 * arguments
	 * 
	 * @return
	 */
	List<String> getArgsOrder();

	/**
	 * @return True if the script can be run
	 */
	boolean canRun();

	/**
	 * @return True if the arguments respects the rules specified in the technical
	 *         conception
	 */
	boolean areArgsValid();

	/**
	 * @return True if the script file exists
	 */
	boolean shellScriptExists();
}
