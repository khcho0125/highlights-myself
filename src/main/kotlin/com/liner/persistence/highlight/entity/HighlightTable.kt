package com.liner.persistence.highlight.entity

import com.liner.domain.highlight.Highlight
import com.liner.persistence.user.entity.UserTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object HighlightTable : IntIdTable(name = "tbl_highlight") {
    val content: Column<String> = varchar("content", Highlight.CONTENT_LENGTH_LIMIT)
    val userId: Column<EntityID<Int>> = reference("user_id", UserTable)
}
