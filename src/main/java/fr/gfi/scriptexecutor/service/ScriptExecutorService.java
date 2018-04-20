package fr.gfi.scriptexecutor.service;

import org.springframework.stereotype.Component;

import fr.gfi.scriptexecutor.service.context.IContext;
import fr.gfi.scriptexecutor.service.scripts.ExecutionResult;

@Component
public interface ScriptExecutorService {
	ExecutionResult execute(IContext context);
	
}
