package fr.gfi.scriptexecutor.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableWebSecurity
@Order(1)

public class APISecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${auth.header}")
	private String principalRequestHeader;

	@Value("${auth.secret-key}")
	private String principalRequestValue;

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		final APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
		filter.setAuthenticationManager(new AuthenticationManager() {

			@Override
			public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
				final String principal = (String) authentication.getPrincipal();
				if (!principalRequestValue.equals(principal)) {
					throw new BadCredentialsException("You must specify a valid secret key in header.");
				}
				authentication.setAuthenticated(true);
				return authentication;
			}
		});
		httpSecurity.antMatcher("/**").csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilter(filter).authorizeRequests()
				.anyRequest().authenticated();
	}

}