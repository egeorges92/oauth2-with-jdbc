package com.dazito.oauthexample.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.dazito.oauthexample.repository.ClientDetailsRepository;
import com.dazito.oauthexample.repository.UserDetailsRepository;

/**
 * Created by daz on 27/06/2017.
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final transient Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfig.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private TokenStore tokenStore;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService());
	}
	
	@Bean
	public ClientDetailsService clientDetailsService() {
        return new ClientDetailsService() {
            @Override
            public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
            	return clientDetailsRepository
        				.findById(clientId)
        				.orElseThrow(() -> new NoSuchClientException("Could not find " + clientId));
            }
        };
    }
	
	@Bean
	public  UserDetailsService userDetailsService() {
        return new UserDetailsService() {
        	@Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsRepository
                        .findById(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
            }
        };
    }

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/*
		 * Allow our tokens to be delivered from our token access point as well as for
		 * tokens to be validated from this point
		 */
		 
		// @formatter:off
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients();
		// @formatter:on
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// @formatter:off
		endpoints
			.authenticationManager(authenticationManager)
			.tokenStore(tokenStore)
			.userDetailsService(userDetailsService());
		// @formatter:on
	}

	@Configuration
	static class JdbcTokenStoreConfiguration {

		private final DataSource dataSource;

		@Autowired
		public JdbcTokenStoreConfiguration(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@Bean
		@Primary
		TokenStore tokenStore() {
			LOGGER.info("Initializing authorization server JDBC token store");
			return new JdbcTokenStore(dataSource);
		}
	}

}
