package fr.gfi.scriptexecutor.service;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gfi.scriptexecutor.exception.ServiceException;
import fr.gfi.scriptexecutor.model.ScriptContext;
import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptExecutorServiceImpl implements ScriptExecutorService {

	private static final Logger log = LoggerFactory.getLogger(ScriptExecutorServiceImpl.class);

	public static final String SCRIPT_NOT_FOUND = "script-not-found";
	public static final String SCRIPT_EXECUTION_ERROR = "script-exec-error";
	public static final String SCRIPT_DONT_RESPECT_RULES = "script-dont-respect-rules";
	public static final String PATTERN = "\\\\{.*\\\\}";

	@Autowired
	private ScriptProvider provider;

	public int execute(ScriptContext context) throws ServiceException {
		ShellScript script = provider.getScripts().get(context.getScriptId());
		if (script == null) {
			log.error("The script {} doesn't exist or is not executable", context.getScriptId());
			throw new ServiceException(SCRIPT_NOT_FOUND);
		}

		List<String> commands = new ArrayList<>();
		commands.add("/bin/bash");
		commands.add(StringUtils.appendIfMissing(provider.getScriptsFolder(), "/") + script.getFilename());

		ProcessBuilder pb = new ProcessBuilder(commands);
		if (context.getArgs() != null && !context.getArgs().isEmpty()) {
			pb.environment().putAll(context.getArgs());
		}
		pb.redirectOutput(Redirect.INHERIT);
		pb.redirectErrorStream(true);
		log.info("The command that will be executed is {}", commands);
		int exitCode = 0;
		try {
			log.error("Executing script : {}", context.getScriptId());
			Process p = pb.start();
			exitCode = p.waitFor();
			log.error("Exit code : {}", exitCode);
			p.destroy();
		} catch (IOException | InterruptedException e) {
			throw new ServiceException(SCRIPT_EXECUTION_ERROR, e);
		}
		return exitCode;
	}

	/**
	 * Gets the last couple of brackets to be parsed
	 * 
	 * @param scriptOutput
	 *            The output generated by the script (with echo command)
	 * @return The last couple of brackets
	 * @throws ServiceException
	 *             If there are no couple of brackets A {"message": "", "messageKey" : ""} must be at least provided by
	 *             the script
	 */
	public String getExecutionResultJSONString(String scriptOutput) throws ServiceException {
		Matcher m = Pattern.compile(PATTERN).matcher(scriptOutput);
		if (m.find()) {
			return m.group(m.groupCount());
		}

		throw new ServiceException(SCRIPT_DONT_RESPECT_RULES);
	}

}
