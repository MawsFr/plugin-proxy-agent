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
import fr.gfi.scriptexecutor.model.ScriptContext;
import fr.gfi.scriptexecutor.service.ProxyAgentService;

/**
 * An agent that executes scripts on the current host.
 *
 */
@RestController("script-executor")
@Produces(MediaType.APPLICATION_JSON)
public class ProxyAgentResource {

	/**
	 * A logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ProxyAgentResource.class);

	/**
	 * The service.
	 */
	@Autowired
	private ProxyAgentService service;

	/**
	 * For testing that the proxy agent is running
	 */
	@GetMapping
	public String hello() {
		return "Proxy agent running";
	}

	/**
	 * Executes a script on the current host.
	 * 
	 * @param context
	 *            The context containing the script id and the parameters sent.
	 * @return The exit code of the script.
	 */
	@PostMapping
	public int execute(@RequestBody final ScriptContext context) throws BusinessException {
		log.info("Trying to execute the script {} with arguments : {}", context.getScriptId(), context.getParameters());
		try {
			return this.service.execute(context);
		} catch (final ServiceException e) {
			throw new BusinessException(e);
		}
	}

}
