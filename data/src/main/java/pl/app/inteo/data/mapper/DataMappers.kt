package pl.app.inteo.data.mapper

import pl.app.inteo.data.models.BuiltByEntity
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity
import pl.app.inteo.domain.models.BuiltBy
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository

fun RepositoryEntity.toDomain(): Repository = Repository(
    forks,
    starsSince,
    builtBy.toDomain(),
    totalStars,
    rank,
    description,
    language,
    languageColor,
    repositoryName,
    url,
    username,
    since,
    RepositoriesType.values()[repositoriesTypeEntity.ordinal]
)

fun Repository.toEntity(): RepositoryEntity = RepositoryEntity(
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
    repositoriesType.toEntity()
)


fun RepositoriesTypeEntity.toDomain(): RepositoriesType = when (this) {
    RepositoriesTypeEntity.DAILY -> RepositoriesType.DAILY
    RepositoriesTypeEntity.MONTHLY -> RepositoriesType.MONTHLY
    RepositoriesTypeEntity.WEEKLY -> RepositoriesType.WEEKLY
    RepositoriesTypeEntity.UNKNOWN -> RepositoriesType.UNKNOWN
}

fun RepositoriesType.toEntity(): RepositoriesTypeEntity = when (this) {
    RepositoriesType.DAILY -> RepositoriesTypeEntity.DAILY
    RepositoriesType.MONTHLY -> RepositoriesTypeEntity.MONTHLY
    RepositoriesType.WEEKLY -> RepositoriesTypeEntity.WEEKLY
    RepositoriesType.UNKNOWN -> RepositoriesTypeEntity.UNKNOWN
}

fun List<BuiltByEntity>.toDomain(): List<BuiltBy> =
    map { BuiltBy(it.avatar, it.url, it.username) }

fun List<BuiltBy>.toEntity(): List<BuiltByEntity> =
    map { BuiltByEntity(it.avatar, it.url, it.username) }
