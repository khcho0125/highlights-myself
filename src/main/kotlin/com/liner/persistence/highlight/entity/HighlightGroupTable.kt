package com.liner.persistence.highlight.entity

import com.liner.persistence.collection.entity.CollectionTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object HighlightGroupTable : Table(name = "tbl_highlight_group") {

    val highlightId: Column<EntityID<Int>> = reference("highlight_id", HighlightTable)
    val collectionId: Column<EntityID<Int>> = reference("collection_id", CollectionTable)

    override val primaryKey: PrimaryKey = PrimaryKey(highlightId, collectionId)
}