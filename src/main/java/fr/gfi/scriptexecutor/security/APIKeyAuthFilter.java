/*
 * Licensed under MIT (https://github.com/ligoj/ligoj/blob/master/LICENSE)
 */
package fr.gfi.scriptexecutor.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * A filter that gets the secret key.
 */
public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

	/**
	 * The header.
	 */
	private final String principalRequestHeader;

	public APIKeyAuthFilter(final String principalRequestHeader) {
		this.principalRequestHeader = principalRequestHeader;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {
		return request.getHeader(principalRequestHeader);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {
		return "N/A";
	}

}