/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * The context sent by the front. Contains the id of the script to execute and the input parameters.
 *
 */
@Getter
@Setter
public class ScriptContext {

	/**
	 * The id of the script to execute.
	 */
	private String scriptId;

	/**
	 * The parameters to pass to the script as environment variables
	 */
	private Map<String, String> parameters;

}
