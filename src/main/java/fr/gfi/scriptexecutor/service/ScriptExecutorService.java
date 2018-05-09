package fr.gfi.scriptexecutor.service;

import org.springframework.stereotype.Component;

import fr.gfi.scriptexecutor.service.context.IContext;
import fr.gfi.scriptexecutor.service.scripts.ExecutionResult;

@Component
public interface ScriptExecutorService {
	/**
	 * Executes a shell script
	 * 
	 * @param context
	 *            the context containing the name of the script to execute and the
	 *            parameters to be passed as environment variable to the script.
	 * @return An execution summary variable containing the messagr to display and
	 *         the code returned by the script.
	 */
	ExecutionResult execute(IContext context);

	/**
	 * Initializes all the callable scripts.
	 */
	void initScripts();

}
