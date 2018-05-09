package fr.gfi.scriptexecutor.mocks.script;

import fr.gfi.scriptexecutor.service.scripts.RepositoryCreationScript;

public class TestSuccess extends RepositoryCreationScript {

	@Override
	public boolean areArgsValid() {
		return false;
	}

	@Override
	public boolean shellScriptExists() {
		return true;
	}

}
