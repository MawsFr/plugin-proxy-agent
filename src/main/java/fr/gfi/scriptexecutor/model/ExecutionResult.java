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
	 * The message to display in the front if no internationalized message is found.
	 */
	protected String message;

	/**
	 * The key of the message to be displayed in the front.
	 */
	protected String messageKey;

	/**
	 * The exit code of the script.
	 */
	protected Integer exitCode;

}
