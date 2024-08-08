package com.dnight.calinify.user.repository

import com.dnight.calinify.user.entity.AccountLinkEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountLinkRepository: JpaRepository<AccountLinkEntity, Long>