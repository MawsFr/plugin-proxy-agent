package fr.gfi.scriptexecutor.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptContext {

	private String scriptId;
	private Map<String, String> args;

	public ScriptContext() {
		args = new HashMap<>();
	}

}
