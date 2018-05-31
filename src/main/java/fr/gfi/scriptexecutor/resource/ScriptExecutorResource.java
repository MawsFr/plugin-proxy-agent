package fr.gfi.scriptexecutor.resource;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	private static final Logger log = LoggerFactory.getLogger(ScriptExecutorResource.class);

	@Autowired
	private ScriptExecutorService service;

	@GetMapping
	public String hello() {
		return "hello world";
	}

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
	@PostMapping
	public ExecutionResult execute(@RequestBody ScriptContext context) throws BusinessException {
		log.info("Trying to execute the script {} with arguments : {}", context.getScriptId(), context.getArgs());
		try {
			return this.service.execute(context);
		} catch (ServiceException e) {
			throw new BusinessException(e);
		}
	}

}
