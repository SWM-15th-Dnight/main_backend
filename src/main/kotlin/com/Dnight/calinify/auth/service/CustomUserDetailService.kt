package com.dnight.calinify.auth.service

import com.dnight.calinify.auth.entity.CustomUserEntity
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.user.entity.UserEntity
import com.dnight.calinify.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    /**
     * authentication manager를 통해 자동으로 호출되고 실행되어, DB에 해당 유저가 있는지 파악한다
     *
     * createUserDetail 단계에서는 CustomUserEntity를 만들며 자동으로
     *
     * @author 정인모
     */
    override fun loadUserByUsername(userEmail : String) : UserDetails =
        userRepository.findByEmail(userEmail)
            ?.let { createUserDetail(it) }
            ?: throw ClientException(ResponseCode.FailedLogin)

    private fun createUserDetail(user: UserEntity) : UserDetails {
        return CustomUserEntity(
            user.userId!!,
            user.email,
            user.password!!,
            List(1) { SimpleGrantedAuthority(user.role) })
    }
}