package fr.gfi.scriptexecutor.service.scripts;

public class CreateGit extends RepositoryCreationScript {

	@Override
	public boolean areArgsValid() {
		return false;
	}

	@Override
	public boolean shellScriptExists() {
		return false;
	}

}
