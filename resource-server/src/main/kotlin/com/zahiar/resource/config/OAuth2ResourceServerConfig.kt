package com.zahiar.resource.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@EnableResourceServer
class OAuth2ResourceServerConfig : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/**").fullyAuthenticated()

    }

    override fun configure(config: ResourceServerSecurityConfigurer) {
        config.tokenStore(tokenStore())
    }

    @Bean
    fun tokenStore(): JwtTokenStore = JwtTokenStore(accessTokenConverter())

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter = JwtAccessTokenConverter().apply {
        setSigningKey("ASharedKey") //Symmetric key - same as one in Auth server
    }

}