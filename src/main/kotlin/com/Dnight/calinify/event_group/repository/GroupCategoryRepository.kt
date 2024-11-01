package com.dnight.calinify.event_group.repository

import com.dnight.calinify.event_group.entity.GroupCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupCategoryRepository : JpaRepository<GroupCategoryEntity, Int>