package com.liner.application.collection

import com.liner.domain.collection.Collection

val collectionFixture: Collection by lazy {
    Collection(
        id = 1,
        name = "컬렉션",
        parentId = null,
        userId = 1,
    )
}
