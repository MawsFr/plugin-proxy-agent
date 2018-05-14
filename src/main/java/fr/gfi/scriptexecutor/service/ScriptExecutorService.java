package fr.gfi.scriptexecutor.service;

import org.springframework.stereotype.Component;

import fr.gfi.scriptexecutor.exception.ServiceException;
import fr.gfi.scriptexecutor.model.ExecutionResult;
import fr.gfi.scriptexecutor.model.ScriptContext;

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
	 * @throws ServiceException
	 *             If the script isn't found or there has been an error thrown
	 *             during the script execution
	 */
	ExecutionResult execute(ScriptContext context) throws ServiceException;

}
