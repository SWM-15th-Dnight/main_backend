package com.dnight.calinify.calendar.entity

import com.dnight.calinify.calendar.request.CalendarCreateDTO
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "userCalendar")
data class CalendarEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val calendarId : Long? = null,
    val title : String,
    val description : String,
    val userId : Long,
    val timezoneId : String,
    @CreatedDate
    val createdAt : LocalDateTime? = null,
    @LastModifiedDate
    val updatedAt : LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    val calendarType : CalendarType,
    val colorId : Int,
) {
    companion object {
        fun from(calendar: CalendarCreateDTO) : CalendarEntity {
            return CalendarEntity(
                title = calendar.title,
                description = calendar.description,
                userId = calendar.userId,
                timezoneId = calendar.timezone,
                calendarType = calendar.calendarType,
                colorId = calendar.colorId,
            )
        }
    }
}

enum class CalendarType {
    event,
    todo
}