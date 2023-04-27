package com.liner.persistence.highlight.entity

import com.liner.persistence.collection.entity.CollectionTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object HighlightTable : Table(name = "tbl_highlight") {

    val id: Column<EntityID<Int>> = integer("id").autoIncrement().entityId()
    val collectionId: Column<EntityID<Int>> = reference("collection_id", CollectionTable)

    override val primaryKey: PrimaryKey = PrimaryKey(id, collectionId)
}
