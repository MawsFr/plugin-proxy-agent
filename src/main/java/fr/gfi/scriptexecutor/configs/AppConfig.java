package fr.gfi.scriptexecutor.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.gfi.scriptexecutor.service.ScriptExecutorService;
import fr.gfi.scriptexecutor.service.ScriptExecutorServiceImpl;

@Configuration
public class AppConfig {
	@Bean
	public ScriptExecutorService transferService() {
		return new ScriptExecutorServiceImpl();
	}
}