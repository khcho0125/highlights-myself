package com.liner.application.collection.usecase

import com.liner.config.exception.CollectionException
import com.liner.config.exception.UserException
import com.liner.domain.collection.Collection
import com.liner.domain.highlight.HighlightStorage
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.dbQuery
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import com.liner.persistence.user.repository.UserRepository
import kotlinx.serialization.Serializable

class GetCollection(
    private val collectionRepository: CollectionRepository,
    private val highlightStorageRepository: HighlightStorageRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        userId: Int,
        cursorCollectionId: Int?,
        size: Int
    ): Response = dbQuery {

        // 유저 ID 유효성 검사
        if(userRepository.existsById(userId).not()) {
            throw UserException.NotFound()
        }

        // 탐색하는 컬렉션 존재 여부 검사
        cursorCollectionId?.let {
            if(collectionRepository.existsByIdAndUserId(it, userId).not()) {
                throw CollectionException.NotFound()
            }
        }

        // 최상위 컬렉션 쿼리
        val collections: List<Collection> = collectionRepository.findAllByParentIdWithPagination(
            parentId = cursorCollectionId,
            size = size
        )

        // 하위 컬렉션 로드 및 반환
        return@dbQuery collections
            .loadView()
            .let(CollectionData::collections)
            .let(::Response)
    }

    /**
     * ## 컬렉션 로딩
     *
     * ### 현재 계층 컬렉션의 ID를 추출 →
     *
     * ① 하위 계층 컬렉션을 쿼리
     *
     *    컬렉션 ID로 group
     *
     * ② 현재 계층 컬렉션의 하이라이트를 쿼리
     *
     *    컬렉션 ID로 group
     *
     * ### 현재 계층 컬렉션 loop →
     *
     * ① 하위 컬렉션에서 __컬렉션 로딩__
     *
     *    하위 컬렉션 뷰와 하위 계층 전체 하이라이트 집합 receive
     *
     * ② 현재 계층 하이라이트 집합과 하위 계층 전체 하이라이트 집합 병합
     *
     *     현재 계층 전체 하이라이트 집합에 병합
     *
     * ③ 컬렉션 뷰 생성
     *
     * ### loop 반복
     *
     * ### → 현재 계층 컬렉션 뷰와 현재 계층 전체 하이라이트 집합 반환
     */
    private suspend fun List<Collection>?.loadView(): CollectionData {
        if (this.isNullOrEmpty()) {
            return CollectionData.EMPTY
        }

        // 컬렉션 ID 추출
        val collectionIds: List<Int> = this.map(Collection::id)

        // 하위 컬렉션 쿼리
        val subCollections: Map<Int?, List<Collection>> = collectionRepository
            .findAllByParentIds(collectionIds)
            .groupBy(Collection::parentId)

        // 현재 계층 컬렉션의 하이라이트 쿼리
        val highlightSet: Map<Int, List<Int>> = highlightStorageRepository
            .findAllByCollectionIds(collectionIds)
            .groupBy(
                keySelector = HighlightStorage::collectionId,
                valueTransform = HighlightStorage::highlightId
            )

        // 반환할 현재 계층 전체 하이라이트 집합
        val entireHighlightSet: MutableSet<Int> = mutableSetOf()

        val collections: List<CollectionView> = this.map { collection ->

            // 하위 컬렉션에서 컬렉션 로딩
            val children: CollectionData = subCollections[collection.id]
                .loadView()

            // 현재 계층 컬렉션에 포함된 전체 하이라이트 집합
            val collectionHighlightSet: Set<Int> = children.entireHighlightSet
                .union(highlightSet.getOrDefault(collection.id, listOf()))

            // 현재 계층 전체 하이라이트 집합 병합
            entireHighlightSet.addAll(collectionHighlightSet)

            CollectionView(
                collectionId = collection.id,
                name = collection.name,
                highlightCount = collectionHighlightSet.size,
                children = children.collections
            )
        }

        return CollectionData(
            collections = collections,
            entireHighlightSet = entireHighlightSet
        )
    }
    
    data class CollectionData(
        val entireHighlightSet: Set<Int>,
        val collections: List<CollectionView>
    ) {

        companion object {
            val EMPTY: CollectionData = CollectionData(
                entireHighlightSet = setOf(),
                collections = listOf()
            )
        }
    }

    @Serializable
    data class Response(
        val collections: List<CollectionView>
    )

    @Serializable
    data class CollectionView(
        val collectionId: Int,
        val highlightCount: Int,
        val name: String,
        val children: List<CollectionView>
    )
}