package fr.gfi.scriptexecutor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains
 * 
 */
@Getter
@Setter
public class ExecutionResult {

	/**
	 * The message to display in the front.
	 */
	protected String message;
	/**
	 * The exit code of the script.
	 */
	protected Integer exitCode;

}
