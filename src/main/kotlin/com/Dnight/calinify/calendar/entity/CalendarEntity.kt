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
    val userId : Long,
    var title : String,
    var description : String?,
    var timezoneId : String,
    @Enumerated(EnumType.STRING)
    var calendarType : CalendarType,
    var colorId : Int,
    @CreatedDate
    var createdAt : LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    var updatedAt : LocalDateTime = LocalDateTime.now(),
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