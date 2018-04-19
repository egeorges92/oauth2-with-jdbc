package com.dazito.oauthexample.oauth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.dazito.oauthexample.config.AppConfig;

/**
 * Created by daz on 27/06/2017.
 */
@EnableAuthorizationServer
@Configuration
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {
    
    private final AuthenticationManager authenticationManager;
    private final AppConfig appConfig;
    
    @Autowired
    private DataSource dataSource;
    
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Autowired
    public AuthServerOAuth2Config(AuthenticationManager authenticationManager, AppConfig appConfig) {
        this.authenticationManager = authenticationManager;
        this.appConfig = appConfig;
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /*
         * Allow our tokens to be delivered from our token access point as well as for tokens
         * to be validated from this point
         */
        security.checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(appConfig.tokenStore()); // Persist the tokens in the database
    }
}
