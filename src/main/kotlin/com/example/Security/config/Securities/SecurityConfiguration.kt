package com.example.Security.config.Securities

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SecurityConfiguration(
    private var jwtAuthFilter:JwtAuthenticationFilter,
    private var authenticationProvider:AuthenticationProvider,
    private var logoutHandler:LogoutHandler
) {


    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .antMatchers("/api/v1/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler { request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication? -> SecurityContextHolder.clearContext() }
        return http.build()
    }
}