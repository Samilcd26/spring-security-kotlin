package com.example.Security.config.security

import com.example.Security.data.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@RequiredArgsConstructor
class ApplicationConfig(
    @Autowired
    var userRepository: UserRepository
) {

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return UserDetailsService { username: String? ->
            userRepository.findByEmail(username)?.orElseThrow { UsernameNotFoundException("User not found") }
        }
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager? {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}