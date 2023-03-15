package com.example.Security.data.repository

import com.example.Security.data.models.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository:JpaRepository<User,Long> {
    fun findByEmail(email: String?): Optional<User?>?
}