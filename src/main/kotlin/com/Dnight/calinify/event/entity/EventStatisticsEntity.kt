package com.dnight.calinify.event.entity

import jakarta.persistence.*

@Entity
@Table(name = "event_statistics")
class EventStatisticsEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val eventStatisticsId : Long? = 0,

    val inputTypeId: Short,

    val inputTimeTaken : Float
)