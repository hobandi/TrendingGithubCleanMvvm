package pl.app.inteo.remote.mappers

import pl.app.inteo.data.models.BuiltByEntity
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity
import pl.app.inteo.remote.models.RepositoryBuiltByRemoteModel
import pl.app.inteo.remote.models.RepositoryRemoteModel

fun RepositoryRemoteModel.toEntity(repositoriesTypeEntity: RepositoriesTypeEntity): RepositoryEntity =
    RepositoryEntity(
        forks,
        starsSince,
        builtBy.toEntity(),
        totalStars,
        rank,
        description,
        language,
        languageColor,
        repositoryName,
        url,
        username,
        since,
        repositoriesTypeEntity
    )

fun List<RepositoryBuiltByRemoteModel>.toEntity(): List<BuiltByEntity> =
    map { BuiltByEntity(it.avatar, it.url, it.username) }

fun RepositoriesTypeEntity.toRemote(): String = when (this) {
    RepositoriesTypeEntity.DAILY -> "daily"
    RepositoriesTypeEntity.WEEKLY -> "weekly"
    RepositoriesTypeEntity.MONTHLY -> "monthly"
    RepositoriesTypeEntity.UNKNOWN -> "daily"
}
