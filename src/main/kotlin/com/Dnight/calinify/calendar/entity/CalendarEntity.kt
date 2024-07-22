package com.dnight.calinify.calendar.entity

import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "calendars")
data class CalendarEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val calendarId : Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user : UserEntity,

    @Column(nullable = false, length = 50)
    var title : String,

    @Column(nullable = true, length = 255)
    var description : String?,

    @Column(nullable = false, length = 20)
    var timezoneId : String = "Asia/Seoul",

    var colorSetId : Int,

    @Column(nullable = false)
    var deleted : Short = 0,

    @CreatedDate
    var createdAt : LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt : LocalDateTime = LocalDateTime.now(),
)