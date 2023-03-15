package com.example.Security.config.Securities

import com.example.Security.data.repository.SecurityRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    var jwtService:JwtService2,
    var userDetailsService: UserDetailsService,
    var securityRepository: SecurityRepository
): OncePerRequestFilter(
) {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
            var authHeader:String=request.getHeader("Authorization")
            var jwt:String?
            var userEmail:String?


            if (authHeader ==null || !authHeader.startsWith("Bearer")){
                filterChain.doFilter(request, response);
                return;
            }

        jwt = authHeader.substring(7)
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(userEmail)
            val isTokenValid:Boolean  = securityRepository.findByToken(jwt)
                ?.map { t -> !t?.expired!! && !t?.revoked!!}
                ?.orElse(false) == true

            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)

    }
}