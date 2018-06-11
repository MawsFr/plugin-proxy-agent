/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.service;

import org.springframework.stereotype.Component;

import fr.gfi.scriptexecutor.exception.ServiceException;
import fr.gfi.scriptexecutor.model.ScriptContext;

/**
 * The proxy agent service.
 *
 */
@Component
public interface ProxyAgentService {
	/**
	 * Executes a shell script on the current host.
	 * 
	 * @param context
	 *            the context containing the name of the script to execute and the parameters to be passed as
	 *            environment variable to the script.
	 * @return The exit code of the executed script.
	 */
	int execute(ScriptContext context) throws ServiceException;

}
