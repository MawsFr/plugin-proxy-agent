/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.gfi.scriptexecutor.service.ProxyAgentService;
import fr.gfi.scriptexecutor.service.ProxyAgentServiceImpl;
import fr.gfi.scriptexecutor.service.ScriptProvider;
import fr.gfi.scriptexecutor.service.ScriptProviderImpl;

/**
 * Contains functions that return implementations of the proxy-agent interfaces.
 *
 */
@Configuration
public class AppConfig {
	@Bean
	public ProxyAgentService transferService() {
		return new ProxyAgentServiceImpl();
	}

	@Bean
	public ScriptProvider scriptProvider() {
		return new ScriptProviderImpl();
	}
}