package fr.gfi.scriptexecutor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.gfi.scriptexecutor.model.ExecutionResult;
import fr.gfi.scriptexecutor.model.ScriptContext;
import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptExecutorServiceImpl implements ScriptExecutorService {

	@Autowired
	private ScriptProvider provider;

	public ExecutionResult execute(ScriptContext context) {
		ShellScript script = provider.getScripts().get(context.getScriptId());
		ExecutionResult result = new ExecutionResult();

		List<String> commands = new ArrayList<>();
		commands.add(script.getFolder() + script.getScriptName());

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.environment().putAll(context.getArgs());
		try {
			Process p = pb.start();
			int exitCode = p.waitFor();
			p.destroy();
			// TODO : Maybe use a regex to get the message + exitcode with lastIndexOf
			// {"message" :
			String output = IOUtils.toString(p.getInputStream(), "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(output, ExecutionResult.class);
			result.setExitCode(exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

}
