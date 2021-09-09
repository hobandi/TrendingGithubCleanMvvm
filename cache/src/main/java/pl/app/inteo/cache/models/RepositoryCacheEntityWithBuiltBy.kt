package pl.app.inteo.cache.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class RepositoryCacheEntityWithBuiltBy(
    @Embedded val repository: RepositoryCacheEntity,
    @Relation(
        parentColumn = "repositoryName",
        entityColumn = "parentRepositoryName"
    )
    val buildBy: List<BuiltByCacheEntity>
)
