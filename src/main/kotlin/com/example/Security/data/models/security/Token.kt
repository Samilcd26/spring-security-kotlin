package com.example.Security.data.models.security

import com.example.Security.data.models.entity.User
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Token(
    @Id
    @GeneratedValue
    var id:Long?=null,

    @Column(unique = true)
    var token:Long?=null,

    @Enumerated(EnumType.STRING)
    var tokenType:TokenType=TokenType.BEARER,
    var revoked:Boolean?=null,
    var expired:Boolean?=null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User?=null,

)
