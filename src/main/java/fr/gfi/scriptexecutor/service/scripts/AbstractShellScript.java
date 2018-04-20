package fr.gfi.scriptexecutor.service.scripts;

import java.util.ArrayList;
import java.util.List;

import fr.gfi.scriptexecutor.service.context.IContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractShellScript implements ShellScript {
	public static final String EXECUTE_SCRIPT = "/bin/bash/";

	protected List<String> argsOrder;
	protected IContext context;

	public AbstractShellScript() {
		argsOrder = new ArrayList<>();
	}

	public boolean canRun() {
		return areArgsValid() && shellScriptExists();
	}

	public ExecutionResult execute(IContext context) {
		this.context = context;
		ExecutionResult result = new ExecutionResult();

		List<String> commands = new ArrayList<>();
		commands.add(EXECUTE_SCRIPT);
		commands.add(getScriptName());

		argsOrder.stream().forEach(arg -> {
			commands.add(context.getArgs().get(arg));
		});

		ProcessBuilder pb = new ProcessBuilder(commands);

		return result;
	}

	// Can be overriden
	public String getScriptName() {
		return context.getScriptId() + ".sh";
	}
}
