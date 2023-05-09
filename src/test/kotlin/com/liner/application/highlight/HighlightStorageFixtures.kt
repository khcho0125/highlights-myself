package com.liner.application.highlight

import com.liner.domain.highlight.HighlightStorage

val highlightStorageFixtures: HighlightStorage by lazy {
    HighlightStorage(
        highlightId = 1,
        collectionId = 1,
    )
}
