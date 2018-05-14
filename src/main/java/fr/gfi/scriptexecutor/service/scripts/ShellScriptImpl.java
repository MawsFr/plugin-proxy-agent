package fr.gfi.scriptexecutor.service.scripts;

import java.io.IOException;
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
public class ShellScriptImpl implements ShellScript {
	private static final Logger logger = LoggerFactory.getLogger(ShellScriptImpl.class);
	public static final String SCRIPT_EXTENSION = ".bat";
	// public static final String SCRIPT_EXTENSION = ".sh";

	protected String name;

	protected String scriptName;

	protected boolean enabled;

	protected String folder;

	// TODO : add rights

	public ShellScriptImpl(String name) {
		this(name, "", name + SCRIPT_EXTENSION);
	}

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically setted to name.sh
	 * 
	 * @param name
	 *            The name of the script
	 */
	public ShellScriptImpl(String name, String folder) {
		this(name, folder, name + SCRIPT_EXTENSION);
	}

	/**
	 * Default constructor
	 * 
	 * The script name will be automatically setted to name.sh
	 * 
	 * @param name
	 *            The name of the script
	 * 
	 * @param scriptName
	 *            The name of the script file to execute
	 */
	public ShellScriptImpl(String name, String folder, String scriptName) {
		this.name = name;
		this.scriptName = scriptName;
		this.folder = folder;
		this.enabled = true;
	}

	public ExecutionResult execute(IContext context) {
		ExecutionResult result = new ExecutionResult();

		List<String> commands = new ArrayList<>();
		commands.add(getFolder() + getScriptName());

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.environment().putAll(context.getArgs());
		try {
			Process p = pb.start();
			int exitCode = p.waitFor();
			p.destroy();
			// TODO : Maybe use a regex to get the message + exitcode with lastIndexOf
			// {"message" :
			String output = IOUtils.toString(p.getInputStream(), "UTF-8");
			logger.info(output);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(output, ExecutionResult.class);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		return name;
	}

}
