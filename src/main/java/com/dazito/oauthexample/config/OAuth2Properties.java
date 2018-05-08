package com.dazito.oauthexample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * From https://gist.github.com/ChihoSin/190f1c163aa760d95c429b106bad8705
 *
 */
@ConfigurationProperties(prefix = "lokra.user.oauth2")
public class OAuth2Properties {

	private Jwt jwt;

	private TokenStore tokenStore;

	private String tokenKeyAccess;

	private String checkTokenAccess;

	public TokenStore getTokenStore() {
		return tokenStore;
	}

	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	public Jwt getJwt() {
		return jwt;
	}

	public void setJwt(Jwt jwt) {
		this.jwt = jwt;
	}

	public String getTokenKeyAccess() {
		return tokenKeyAccess;
	}

	public void setTokenKeyAccess(String tokenKeyAccess) {
		this.tokenKeyAccess = tokenKeyAccess;
	}

	public String getCheckTokenAccess() {
		return checkTokenAccess;
	}

	public void setCheckTokenAccess(String checkTokenAccess) {
		this.checkTokenAccess = checkTokenAccess;
	}

	public static enum TokenStore {
		DATABASE, REDIS, IN_MEMORY, JWT
	}

	public static class Jwt {

		private String password;

		private String resource;

		private String keyPair;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getResource() {
			return resource;
		}

		public void setResource(String resource) {
			this.resource = resource;
		}

		public String getKeyPair() {
			return keyPair;
		}

		public void setKeyPair(String keyPair) {
			this.keyPair = keyPair;
		}
	}
}