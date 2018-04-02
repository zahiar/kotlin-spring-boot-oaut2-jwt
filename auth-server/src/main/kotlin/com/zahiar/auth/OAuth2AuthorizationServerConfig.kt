package com.zahiar.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
            .accessTokenConverter(accessTokenConverter())
            .authenticationManager(authenticationManager)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("UserOne").secret("{noop}Password")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
            .authorities("ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "delete")
            .accessTokenValiditySeconds(3600)

            .and()

            .withClient("UserTwo").secret("{noop}Password")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
            .authorities("ROLE_CLIENT")
            .scopes("read")
            .accessTokenValiditySeconds(3600)
    }

    // Allow clients to call the check_token endpoint
    override fun configure(server: AuthorizationServerSecurityConfigurer) {
        server.tokenKeyAccess("permitAll").checkTokenAccess("isAuthenticated()")
    }

    @Bean
    fun tokenStore(): JwtTokenStore = JwtTokenStore(accessTokenConverter())

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter = JwtAccessTokenConverter().apply {
        setSigningKey("ASharedKey") //Symmetric key - same as one in Resource server
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices = DefaultTokenServices().apply {
        setTokenStore(tokenStore())
        setSupportRefreshToken(true)
    }

}