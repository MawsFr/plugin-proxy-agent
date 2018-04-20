package fr.gfi.scriptexecutor.service.scripts;

public abstract class RepositoryCreationScript extends AbstractShellScript {

	public static final String CLIENT_NAME = "client_name";

	public RepositoryCreationScript() {
		super();
		this.argsOrder.add(CLIENT_NAME);
	}

}
