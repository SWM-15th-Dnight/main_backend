package com.dnight.calinify.alarm.repository

import com.dnight.calinify.alarm.entity.AlarmEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AlarmRepository : JpaRepository<AlarmEntity, Long>