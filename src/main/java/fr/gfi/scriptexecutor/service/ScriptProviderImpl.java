/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import fr.gfi.scriptexecutor.model.ShellScript;
import lombok.Getter;
import lombok.Setter;

/**
 * This script provider sets a map of executable script using constants.
 */
@Getter
@Setter
public class ScriptProviderImpl implements ScriptProvider {

	/**
	 * A logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ScriptProviderImpl.class);

	/**
	 * Git repo creation script.
	 */
	public static final String CREATE_GIT = "create_git";
	/**
	 * Svn repo creation script.
	 */
	public static final String CREATE_SVN = "create_svn";
	/**
	 * Git repo existance verification script.
	 */
	public static final String EXISTS_GIT = "exists_git";
	/**
	 * Svn repo existance verification script.
	 */
	public static final String EXISTS_SVN = "exists_svn";

	@Value("${scripts.folder}")
	private String scriptsFolder;

	/**
	 * The Map of executable scripts.
	 */
	private Map<String, ShellScript> scripts;

	public ScriptProviderImpl() {
		scripts = new HashMap<>();
		initScripts();
	}

	@Override
	public void initScripts() {
		addScript(new ShellScript(CREATE_GIT));
		addScript(new ShellScript(CREATE_SVN));
		addScript(new ShellScript(EXISTS_GIT));
		addScript(new ShellScript(EXISTS_SVN));
		log.info("Initialized list of script from folder {}", scriptsFolder);
	}

	@Override
	public void addScript(final ShellScript script) {
		scripts.put(script.getId(), script);
		final File file = new File(StringUtils.appendIfMissing(scriptsFolder, "/") + script.getFilename());
		file.setExecutable(true, false);
		file.setWritable(true, false);
		file.setReadable(true, false);

	}

}
