package fr.gfi.scriptexecutor.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.gfi.scriptexecutor.exception.ServiceException;
import fr.gfi.scriptexecutor.model.ExecutionResult;
import fr.gfi.scriptexecutor.model.ScriptContext;
import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptExecutorServiceImpl implements ScriptExecutorService {

	public static final String SCRIPT_NOT_FOUND = "script-notfound";

	@Autowired
	private ScriptProvider provider;

	public ExecutionResult execute(ScriptContext context) throws ServiceException {
		ShellScript script = provider.getScripts().get(context.getScriptId());
		if (script == null) {
			throw new ServiceException(SCRIPT_NOT_FOUND);
		}
		ExecutionResult result = new ExecutionResult();

		List<String> commands = new ArrayList<>();
		commands.add(provider.getScriptsFolder() + script.getFilename());

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.environment().putAll(context.getArgs());
		try {
			Process p = pb.start();
			int exitCode = p.waitFor();
			p.destroy();
			// TODO : Maybe use a regex to get the message + exitcode with lastIndexOf
			// {"message" :
			String output = IOUtils.toString(p.getInputStream(), StandardCharsets.UTF_8.name());
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(output, ExecutionResult.class);
			result.setExitCode(exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

}
