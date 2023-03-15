package com.example.Security.data.models.entity

import com.example.Security.data.models.security.Token
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
data class User(
    @Id
    private var id: Long? = null,
    private var firstName: String? = null,
    private var lastName: String? = null,
    private var email:String?=null,
    private var password:String?=null,

    @Enumerated(EnumType.STRING)
    private var role: Roles?=null,

    @OneToMany(mappedBy = "user",cascade = [(CascadeType.ALL)],fetch = FetchType.LAZY)
    private var token:List<Token>?=null,
):UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf( SimpleGrantedAuthority(role?.name))
    }
    override fun getPassword(): String? {
       return password
    }

    override fun getUsername(): String? {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
