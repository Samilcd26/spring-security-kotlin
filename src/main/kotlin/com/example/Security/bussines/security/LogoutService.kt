package com.example.Security.bussines.security

import com.example.Security.data.repository.SecurityRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutService(
    private var securityRepository: SecurityRepository
) :LogoutHandler{
    override fun logout(request: HttpServletRequest, response: HttpServletResponse?, authentication: Authentication?) {
        var authHeader:String=request.getHeader("Authorization")
        var jwt:String

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = securityRepository.findByToken(jwt)?.orElse(null)

        if (storedToken != null) {
            storedToken.revoked=true
            storedToken.revoked=true
            securityRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}