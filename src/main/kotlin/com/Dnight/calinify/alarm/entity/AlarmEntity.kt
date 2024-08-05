package com.dnight.calinify.alarm.entity

import com.dnight.calinify.config.basicEntity.BasicEntity
import jakarta.persistence.*

@Entity
@Table(name = "alarm")
class AlarmEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val alarmId: Long? = 0,

    @Enumerated(EnumType.STRING)
    var action: AlarmAction = AlarmAction.DISPLAY,

    @Column(nullable = false)
    var alarmTrigger: String = "B15;",

    @Column(nullable = false)
    var description: String,
): BasicEntity()