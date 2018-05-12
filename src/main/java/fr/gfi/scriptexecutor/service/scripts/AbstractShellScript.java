package fr.gfi.scriptexecutor.service.scripts;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.gfi.scriptexecutor.service.context.IContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractShellScript implements ShellScript {
	private static final Logger logger = LoggerFactory.getLogger(AbstractShellScript.class);

	public static final String EXECUTE_SCRIPT = "/bin/bash";

	protected IContext context;

	public boolean canRun() {
		return areArgsValid() && shellScriptExists();
	}

	public ExecutionResult execute(IContext context) {
		this.context = context;
		ExecutionResult result = new ExecutionResult();

		List<String> commands = new ArrayList<>();
//		commands.add(EXECUTE_SCRIPT);
		commands.add(getScriptName());

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.environment().putAll(context.getArgs());
//		pb.redirectErrorStream(true);
//		pb.redirectOutput(Redirect.INHERIT);
		try {
			Process p = pb.start();
			p.waitFor();
			String output = IOUtils.toString(p.getInputStream(), "UTF-8");
			logger.info(output);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(output, ExecutionResult.class);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	// Can be overriden
	public String getScriptName() {
		return "src/test/resources/scripts/" + context.getScriptId() + ".bat";
	}
}
