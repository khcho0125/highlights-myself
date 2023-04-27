package com.liner.persistence.highlight.repository

import com.liner.domain.highlight.Highlight

interface HighlightRepository {

    suspend fun findById(id: Int) : Highlight?

    suspend fun insert(highlight: Highlight): Int
}