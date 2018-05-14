package fr.gfi.scriptexecutor.resource;

import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.gfi.scriptexecutor.exception.BusinessException;
import fr.gfi.scriptexecutor.exception.ServiceException;
import fr.gfi.scriptexecutor.model.ExecutionResult;
import fr.gfi.scriptexecutor.model.ScriptContext;
import fr.gfi.scriptexecutor.service.ScriptExecutorService;

@RestController("script-executor")
@Produces(MediaType.APPLICATION_JSON)
public class ScriptExecutorResource {

	@Autowired
	private ScriptExecutorService service;

	/**
	 * Executes a script
	 * 
	 * @param scriptId
	 *            The script id
	 * @param params
	 *            The list of field needed by the script as env variables
	 * @return The execution result with the error message
	 * @throws BusinessException
	 */
	@PostMapping("/{scriptId}")
	public ExecutionResult execute(@PathVariable("scriptId") String scriptId, @RequestBody Map<String, String> params)
			throws BusinessException {
		ScriptContext context = createContext(scriptId, params);
		try {
			return this.service.execute(context);
		} catch (ServiceException e) {
			throw new BusinessException(e);
		}
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
	private ScriptContext createContext(String scriptId, Map<String, String> params) {
		ScriptContext context = new ScriptContext();
		context.setScriptId(scriptId);
		context.setArgs(params);
		return context;
	}

}
