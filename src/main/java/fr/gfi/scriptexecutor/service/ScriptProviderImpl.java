package fr.gfi.scriptexecutor.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptProviderImpl implements ScriptProvider {
	// TODO : Create a ScriptExecutorContext that will contain all executable
	// scripts
	public static final String CREATE_GIT = "create_git";
	public static final String CREATE_SVN = "create_svn";

	@Value("${scripts.folder}")
	private String scriptsFolder;

	private Map<String, ShellScript> scripts;

	public ScriptProviderImpl() {
		scripts = new HashMap<>();
		initScripts();
	}

	public void initScripts() {
		addScript(new ShellScript(CREATE_GIT));
		addScript(new ShellScript(CREATE_SVN));
	}

	@Override
	public void addScript(ShellScript script) {
		scripts.put(script.getId(), script);
	}

}
