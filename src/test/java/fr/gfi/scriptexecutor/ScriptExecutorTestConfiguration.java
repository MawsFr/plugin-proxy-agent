package fr.gfi.scriptexecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import fr.gfi.scriptexecutor.mocks.service.ScriptExecutorServiceMockImpl;
import fr.gfi.scriptexecutor.service.ScriptExecutorService;

@Profile(ScriptExecutorApplicationTests.TEST_PROFILE)
@Configuration
public class ScriptExecutorTestConfiguration {
	@Bean
	@Primary
	public ScriptExecutorService scriptExecutorService() {
		return new ScriptExecutorServiceMockImpl();
	}
}