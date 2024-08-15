package com.dnight.calinify.event.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "event_statistics")
class EventStatisticsEntity(
    @Id
    @Column(name = "event_statistics_id", nullable = false)
    var eventStatisticsId: Long? = null,


)