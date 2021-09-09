package pl.app.inteo.cache.models

import androidx.room.Embedded
import androidx.room.Relation

data class BookmarkedRepositoriesRelation(
    @Embedded val repository: RepositoryCacheEntity?,
    @Relation(
        parentColumn = "repositoryName",
        entityColumn = "parentRepositoryName"
    )
    val bookmarked: BookmarkedEntity?
)