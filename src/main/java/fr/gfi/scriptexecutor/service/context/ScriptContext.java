package fr.gfi.scriptexecutor.service.context;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptContext implements IContext {

	private String scriptId;
	private Map<String, String> args;

	public ScriptContext() {
		args = new HashMap<>();
	}

}
