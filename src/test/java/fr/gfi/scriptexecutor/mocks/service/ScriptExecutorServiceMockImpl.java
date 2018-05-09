package fr.gfi.scriptexecutor.mocks.service;

import java.util.HashMap;
import java.util.Map;

import fr.gfi.scriptexecutor.mocks.script.TestSuccess;
import fr.gfi.scriptexecutor.service.ScriptExecutorService;
import fr.gfi.scriptexecutor.service.context.IContext;
import fr.gfi.scriptexecutor.service.scripts.ExecutionResult;
import fr.gfi.scriptexecutor.service.scripts.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptExecutorServiceMockImpl implements ScriptExecutorService {
	public static final String TEST_SUCCESS = "testsuccess";

	// TODO : String to Enum
	private Map<String, ShellScript> scripts;

	public ScriptExecutorServiceMockImpl() {
		scripts = new HashMap<>();
		initScripts();
	}

	public void initScripts() {
		scripts.put(TEST_SUCCESS, new TestSuccess());
	}

	public ExecutionResult execute(IContext context) {
		return this.scripts.get(context.getScriptId()).execute(context);
	}

}
