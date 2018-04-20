package fr.gfi.scriptexecutor.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gfi.scriptexecutor.service.ScriptExecutorService;

@RestController("script-executor")
public class ServiceExecutorResource {
	
//	public static final String PATH = "script-executor";
	
	@Autowired
	private ScriptExecutorService service;
	
	@GetMapping
	public String helloWorld() {
		return "Hello World";
	}
	
}
