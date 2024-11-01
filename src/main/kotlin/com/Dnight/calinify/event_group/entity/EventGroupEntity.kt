package com.dnight.calinify.event_group.entity

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

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val user : UserEntity,

    @Column(nullable = false)
    var colorSetId : Int,

    @JoinColumn(name = "group_category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var groupCategory : GroupCategoryEntity,
) : BasicEntity()