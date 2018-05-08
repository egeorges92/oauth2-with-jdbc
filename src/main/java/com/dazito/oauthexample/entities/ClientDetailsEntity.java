package com.dazito.oauthexample.entities;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "oauth_client_details")
public class ClientDetailsEntity implements ClientDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 324498523479694906L;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Id
	@Column(name="client_id")
	private String clientId;
	
	@Column(name="resource_ids")
	private String resourceIds;
	
	@Column(name="client_secret")
	private String clientSecret;
	
	@Column(name="scope")
	private String scope;
	
	@Column(name="authorized_grant_types", nullable = false)
	private String authorizedGrantTypes;
	
	@Column(name="web_server_redirect_uri")
	private String registeredRedirectUri;
	
	@Column(name="authorities")
	private String authorities;
	
	@Column(name="access_token_validity", nullable = false)
	private Integer accessTokenValiditySeconds;
	
	@Column(name="refresh_token_validity", nullable = false)
	private Integer refreshTokenValiditySeconds;
	
	@Column(name="autoapprove")
	private String autoApproveScope;
	
	@Column(name="additional_information")
	private String additionalInformation;

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public void setRegisteredRedirectUri(String registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public void setAutoApproveScope(String autoApproveScope) {
		this.autoApproveScope = autoApproveScope;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	@Override
	public String getClientId() {
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		if (StringUtils.isEmpty(this.resourceIds)) {
			return new HashSet<>();
		} else {
			return StringUtils.commaDelimitedListToSet(this.resourceIds);
		}
	}

	@Override
	public boolean isSecretRequired() {
		return !StringUtils.isEmpty(this.clientSecret);
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	@Override
	public boolean isScoped() {
		return this.getScope().size() > 0;
	}

	@Override
	public Set<String> getScope() {
		return StringUtils.commaDelimitedListToSet(this.scope);
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return StringUtils.commaDelimitedListToSet(this.authorizedGrantTypes);
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return StringUtils.commaDelimitedListToSet(this.registeredRedirectUri);
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<String> set = StringUtils.commaDelimitedListToSet(this.authorities);
		Set<GrantedAuthority> result = new HashSet<>();
		set.forEach(authority -> result.add(new GrantedAuthority() {
			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -1786626705526474408L;

			@Override
			public String getAuthority() {
				return authority;
			}
		}));
		return result;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return this.getAutoApproveScope().contains(scope);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAdditionalInformation() {
		try {
			return mapper.readValue(this.additionalInformation, Map.class);
		} catch (IOException e) {
			return new HashMap<>();
		}
	}

	public Set<String> getAutoApproveScope() {
		return StringUtils.commaDelimitedListToSet(this.autoApproveScope);
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = StringUtils.collectionToCommaDelimitedString(resourceIds);
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setScope(Set<String> scope) {
		this.scope = StringUtils.collectionToCommaDelimitedString(scope);
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantType) {
		this.authorizedGrantTypes = StringUtils.collectionToCommaDelimitedString(authorizedGrantType);
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUriList) {
		this.registeredRedirectUri = StringUtils.collectionToCommaDelimitedString(registeredRedirectUriList);
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = StringUtils.collectionToCommaDelimitedString(authorities);
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public void setAutoApproveScope(Set<String> autoApproveScope) {
		this.autoApproveScope = StringUtils.collectionToCommaDelimitedString(autoApproveScope);
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		try {
			this.additionalInformation = mapper.writeValueAsString(additionalInformation);
		} catch (IOException e) {
			this.additionalInformation = "";
		}
	}
}