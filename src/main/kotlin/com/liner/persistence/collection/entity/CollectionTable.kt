package com.liner.persistence.collection.entity

import com.liner.domain.collection.Collection
import com.liner.persistence.user.entity.UserTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object CollectionTable : IntIdTable(name = "tbl_collection") {
    val userId: Column<EntityID<Int>> = reference("user_id", UserTable)
    val name: Column<String> = varchar("name", Collection.NAME_LENGTH_LIMIT)
    val parentId: Column<EntityID<Int>?> = reference("parent_id", CollectionTable).nullable()
}
