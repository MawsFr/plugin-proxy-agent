package fr.gfi.scriptexecutor.service;

import java.util.HashMap;
import java.util.Map;

import fr.gfi.scriptexecutor.service.context.IContext;
import fr.gfi.scriptexecutor.service.scripts.CreateGit;
import fr.gfi.scriptexecutor.service.scripts.CreateSVN;
import fr.gfi.scriptexecutor.service.scripts.ExecutionResult;
import fr.gfi.scriptexecutor.service.scripts.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptExecutorServiceImpl implements ScriptExecutorService {
	public static final String CREATE_GIT = "create_git";
	public static final String CREATE_SVN = "create_svn";

	// TODO : String to Enum
	private Map<String, ShellScript> scripts;

	public ScriptExecutorServiceImpl() {
		scripts = new HashMap<>();
		initScripts();
	}

	private void initScripts() {
		scripts.put(CREATE_GIT, new CreateGit());
		scripts.put(CREATE_SVN, new CreateSVN());
	}

	public ExecutionResult execute(IContext context) {
		return this.scripts.get(context.getScriptId()).execute(context);
	}

}
