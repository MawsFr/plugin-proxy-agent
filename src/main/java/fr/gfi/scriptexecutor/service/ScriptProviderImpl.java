package fr.gfi.scriptexecutor.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptProviderImpl implements ScriptProvider {

	private static final Logger log = LoggerFactory.getLogger(ScriptProviderImpl.class);

	// TODO : Create a ScriptExecutorContext that will contain all executable
	// scripts
	public static final String CREATE_GIT = "create_git";
	public static final String CREATE_SVN = "create_svn";

	@Value("${SCRIPT_FOLDER}")
	private String scriptsFolder;

	private Map<String, ShellScript> scripts;

	public ScriptProviderImpl() {
		scripts = new HashMap<>();
		initScripts();
	}

	public void initScripts() {
		addScript(new ShellScript(CREATE_GIT));
		addScript(new ShellScript(CREATE_SVN));
		log.info("Initialized list of script from folder {}", scriptsFolder);
	}

	@Override
	public void addScript(ShellScript script) {
		scripts.put(script.getId(), script);
	}

}
