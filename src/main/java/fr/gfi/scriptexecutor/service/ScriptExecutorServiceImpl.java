package fr.gfi.scriptexecutor.service;

import org.springframework.beans.factory.annotation.Autowired;

import fr.gfi.scriptexecutor.service.context.IContext;
import fr.gfi.scriptexecutor.service.scripts.ExecutionResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptExecutorServiceImpl implements ScriptExecutorService {

	@Autowired
	private ScriptProvider provider;

	public ExecutionResult execute(IContext context) {
		return provider.getScripts().get(context.getScriptId()).execute(context);
	}

}
