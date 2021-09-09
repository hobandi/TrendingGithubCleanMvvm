package pl.app.inteo.cache.mapper

import pl.app.inteo.cache.models.BuiltByCacheEntity
import pl.app.inteo.cache.models.RepositoryCacheEntity
import pl.app.inteo.data.models.BuiltByEntity
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity

fun RepositoryEntity.toCacheEntity(repositoriesTypeEntity: RepositoriesTypeEntity): RepositoryCacheEntity =
    RepositoryCacheEntity(
        repositoryName,
        forks,
        starsSince,
        builtBy.toCacheEntity(),
        totalStars,
        rank,
        description,
        language,
        languageColor,
        url,
        username,
        since,
        repositoriesTypeEntity.ordinal
    )

fun RepositoryCacheEntity.toEntity(): RepositoryEntity = RepositoryEntity(
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
    RepositoriesTypeEntity.values()[repositoriesType]
)

fun List<BuiltByCacheEntity>.toEntity(): List<BuiltByEntity> =
    map { BuiltByEntity(it.avatar, it.url, it.username) }

fun List<BuiltByEntity>.toCacheEntity(): List<BuiltByCacheEntity> =
    map { BuiltByCacheEntity(it.avatar, it.url, it.username) }
