package com.dnight.calinify.calendar.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "userCalendar")
data class CalendarEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val calendarId : Long,
    val title : String,
    val description : String,
    val userId : Long,
    val timezoneId : String,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
    @Enumerated(EnumType.STRING)
    val calendarType : CalendarType,
    val colorId : Int,
)

enum class CalendarType {
    event,
    todo
}