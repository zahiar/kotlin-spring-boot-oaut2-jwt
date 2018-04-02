package com.zahiar.resource.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class MethodSecurityConfig : GlobalMethodSecurityConfiguration() {

    // Needed to allow use of oauth2 annotations
    override fun createExpressionHandler(): MethodSecurityExpressionHandler = OAuth2MethodSecurityExpressionHandler()

}