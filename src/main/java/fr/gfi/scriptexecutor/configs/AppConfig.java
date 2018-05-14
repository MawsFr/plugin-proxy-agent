package fr.gfi.scriptexecutor.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.gfi.scriptexecutor.service.ScriptExecutorService;
import fr.gfi.scriptexecutor.service.ScriptExecutorServiceImpl;
import fr.gfi.scriptexecutor.service.ScriptProvider;
import fr.gfi.scriptexecutor.service.ScriptProviderImpl;

@Configuration
public class AppConfig {
	@Bean
	public ScriptExecutorService transferService() {
		return new ScriptExecutorServiceImpl();
	}

	@Bean
	public ScriptProvider scriptProvider() {
		return new ScriptProviderImpl();
	}
}