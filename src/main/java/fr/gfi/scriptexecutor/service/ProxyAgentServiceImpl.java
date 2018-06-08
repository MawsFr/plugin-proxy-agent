/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.service;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import fr.gfi.scriptexecutor.exception.ServiceException;
import fr.gfi.scriptexecutor.model.ScriptContext;
import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

/**
 * This service executes shell scripts on host.
 */
@Getter
@Setter
public class ProxyAgentServiceImpl implements ProxyAgentService {

	/**
	 * A logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ProxyAgentServiceImpl.class);

	/**
	 * The script-not-found constant.
	 */
	public static final String SCRIPT_NOT_FOUND = "script-not-found";

	/**
	 * The script-exec-error constant.
	 */
	public static final String SCRIPT_EXECUTION_ERROR = "script-exec-error";

	/**
	 * The shell script interpreter (By default /bin/bash)
	 */
	@Value("${script.interpreter}")
	protected String interpreter;

	/**
	 * The script provider.
	 */
	@Autowired
	private ScriptProvider provider;

	@Override
	public int execute(final ScriptContext context) throws ServiceException {
		// verify script id exists
		if (StringUtils.isBlank(context.getScriptId())) {
			log.error("No script id has been specified");
			throw new ServiceException(SCRIPT_NOT_FOUND);
		}

		final ShellScript script = provider.getScripts().get(context.getScriptId());
		if (script == null) {
			log.error("The script {} doesn't exist or is not executable", context.getScriptId());
			throw new ServiceException(SCRIPT_NOT_FOUND);
		}

		// Create the command
		final List<String> commands = new ArrayList<>();
		commands.add(interpreter);
		commands.add(StringUtils.appendIfMissing(provider.getScriptsFolder(), "/") + script.getFilename());

		final ProcessBuilder pb = new ProcessBuilder(commands);
		final Map<String, String> parameters = context.getParameters();
		// Set parameters as environment variable during script execution
		if (parameters != null && !parameters.isEmpty()) {
			pb.environment().putAll(parameters);
		}

		// Set log stream
		pb.redirectOutput(Redirect.INHERIT);
		pb.redirectErrorStream(true);
		log.info("The command that will be executed is {}", commands);

		// execute script
		int exitCode = 0;
		try {
			log.error("Executing script : {}", context.getScriptId());
			final Process p = pb.start();
			exitCode = p.waitFor();
			log.error("Exit code : {}", exitCode);
			p.destroy();
		} catch (IOException | InterruptedException e) {
			throw new ServiceException(SCRIPT_EXECUTION_ERROR, e);
		}
		return exitCode;
	}
}
