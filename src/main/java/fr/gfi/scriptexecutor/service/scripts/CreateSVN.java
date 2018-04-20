package fr.gfi.scriptexecutor.service.scripts;

public class CreateSVN extends RepositoryCreationScript {

	@Override
	public boolean areArgsValid() {
		return true;
	}

	@Override
	public boolean shellScriptExists() {
		return true;
	}

}
