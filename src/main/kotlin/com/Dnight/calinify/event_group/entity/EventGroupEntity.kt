package com.dnight.calinify.event_group.entity

import com.dnight.calinify.common.colorSets.ColorSetsEntity
import com.dnight.calinify.config.basicEntity.BasicEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "event_group")
class EventGroupEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val groupId : Long? = 0,

    @Column(nullable = false)
    var groupName : String,

    @Column(nullable = true)
    var description : String? = null,

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user : UserEntity,

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var colorSet : ColorSetsEntity

) : BasicEntity()