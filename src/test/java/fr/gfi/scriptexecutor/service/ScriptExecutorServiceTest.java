package fr.gfi.scriptexecutor.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.gfi.scriptexecutor.service.context.ScriptContext;

public class ScriptExecutorServiceTest {
	
	@Autowired
	private ScriptExecutorService service;
	
	@Test
	public void executeSucess() {
		ScriptContext context = new ScriptContext();
		context.setScriptId("test");
	}

}
