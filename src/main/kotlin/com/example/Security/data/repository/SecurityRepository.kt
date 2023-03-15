package com.example.Security.data.repository

import com.example.Security.data.models.security.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SecurityRepository :JpaRepository<Token,Long>{
    @Query(value = """
      select t from Token t inner join User u on t.user.id = u.id 
      where u.id = :id and (t.expired = false or t.revoked = false)
      """)
    fun findAllValidTokenByUser(id: Int?): List<Token?>?

    fun findByToken(token: String?): Optional<Token?>?
}