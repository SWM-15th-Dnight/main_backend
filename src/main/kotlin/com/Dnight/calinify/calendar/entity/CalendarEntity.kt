package com.dnight.calinify.calendar.entity

import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_calendar")
data class CalendarEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val calendarId : Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val user : UserEntity,

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
)

enum class CalendarType {
    event,
    todo
}