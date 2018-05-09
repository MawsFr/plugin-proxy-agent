package fr.gfi.scriptexecutor.resource;

import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.gfi.scriptexecutor.service.ScriptExecutorService;
import fr.gfi.scriptexecutor.service.context.IContext;
import fr.gfi.scriptexecutor.service.context.ScriptContext;
import fr.gfi.scriptexecutor.service.scripts.ExecutionResult;

@RestController("script-executor")
@Produces(MediaType.APPLICATION_JSON)
public class ScriptExecutorResource {

	// public static final String PATH = "script-executor";

	@Autowired
	private ScriptExecutorService service;

	@GetMapping
	public String helloWorld() {
		return "Hello World";
	}

	/**
	 * Executes a script
	 * 
	 * @param scriptId
	 *            The script id
	 * @param params
	 *            The list of field needed by the script as env variables
	 * @return The execution result with the error message
	 */
	@PostMapping("/{scriptId}")
	public ExecutionResult execute(@PathVariable("scriptId") String scriptId, @RequestBody Map<String, String> params) {
		IContext context = createContext(scriptId, params);
		return this.service.execute(context);
	}

	/**
	 * Creates a context variable with the script id and the parameters sent by
	 * front
	 * 
	 * @param scriptId
	 *            The script id
	 * @param params
	 *            The parameters entered in fields (front)
	 * @return A context variable
	 */
	private IContext createContext(String scriptId, Map<String, String> params) {
		ScriptContext context = new ScriptContext();
		context.setScriptId(scriptId);
		context.setArgs(params);
		return context;
	}

}
