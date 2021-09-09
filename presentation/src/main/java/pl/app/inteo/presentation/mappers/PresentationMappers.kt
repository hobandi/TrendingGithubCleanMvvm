package pl.app.inteo.presentation.mappers

import pl.app.inteo.domain.models.BuiltBy
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.models.SortingType
import pl.app.inteo.presentation.model.BuiltByUiModel
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.model.RepositoryUiModel
import pl.app.inteo.presentation.model.SortingTypeUiModel

fun List<Repository>.toPresentationUiModel(): List<RepositoryUiModel> =
    map { it.toPresentationUiModel() }

fun Repository.toPresentationUiModel(): RepositoryUiModel =
    RepositoryUiModel(
        forks,
        starsSince,
        builtBy.toPresentation(),
        totalStars,
        rank,
        description,
        language,
        languageColor,
        repositoryName,
        url,
        username,
        since,
        repositoriesType.toPresentationUiModel()
    )

fun SortingTypeUiModel.toDomain(): SortingType = when (this) {
    SortingTypeUiModel.NO_SORTING -> SortingType.NO_SORTING
    SortingTypeUiModel.TOTAL_STARS -> SortingType.TOTAL_STARS
    SortingTypeUiModel.SINCE_STARS -> SortingType.SINCE_STARS
    SortingTypeUiModel.FORK_COUNT -> SortingType.FORK_COUNT
}

fun List<BuiltBy>.toPresentation(): List<BuiltByUiModel> =
    map { BuiltByUiModel(it.avatar, it.url, it.username) }

fun RepositoriesType.toPresentationUiModel(): RepositoriesTypeUiModel = when (this) {
    RepositoriesType.DAILY -> RepositoriesTypeUiModel.DAILY
    RepositoriesType.MONTHLY -> RepositoriesTypeUiModel.MONTHLY
    RepositoriesType.WEEKLY -> RepositoriesTypeUiModel.WEEKLY
    RepositoriesType.UNKNOWN -> RepositoriesTypeUiModel.UNKNOWN
}

fun RepositoriesTypeUiModel.toDomain(): RepositoriesType = when (this) {
    RepositoriesTypeUiModel.DAILY -> RepositoriesType.DAILY
    RepositoriesTypeUiModel.MONTHLY -> RepositoriesType.MONTHLY
    RepositoriesTypeUiModel.WEEKLY -> RepositoriesType.WEEKLY
    RepositoriesTypeUiModel.UNKNOWN -> RepositoriesType.UNKNOWN
}
