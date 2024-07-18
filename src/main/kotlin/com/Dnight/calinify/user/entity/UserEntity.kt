package com.dnight.calinify.user.entity

import com.dnight.calinify.user.dto.request.Gender
import com.dnight.calinify.user.dto.request.UserCreateRequestDTO
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class UserEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    @Email
    val email: String,
    var userName: String,
    val password: String,
    @CreatedDate
    val createdAt : LocalDateTime? = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt : LocalDateTime? = LocalDateTime.now(),
    @Enumerated(EnumType.STRING)
    val gender: Gender?,
    val phoneNumber: String?
) {
    companion object{
        fun from(userCreateRequestDTO: UserCreateRequestDTO): UserEntity {
            return UserEntity(
                email = userCreateRequestDTO.email,
                password = userCreateRequestDTO.password,
                userName = userCreateRequestDTO.userName,
                gender = userCreateRequestDTO.gender,
                phoneNumber = userCreateRequestDTO.phoneNumber,
            )
        }
    }
}