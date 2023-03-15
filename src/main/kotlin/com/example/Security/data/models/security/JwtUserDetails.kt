package com.example.Security.data.models.security

import org.aspectj.weaver.ast.Var
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class JwtUserDetails(
    private var id:Long?=null,
    private var username:Long?=null,
    private var password:Long?=null,
    private var authorities:ob=null,

):UserDetails() {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}
