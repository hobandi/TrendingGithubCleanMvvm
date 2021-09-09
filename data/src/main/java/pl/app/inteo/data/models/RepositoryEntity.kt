package pl.app.inteo.data.models

data class RepositoryEntity(
    val forks: Int,
    val starsSince: Int,
    val builtBy: List<BuiltByEntity>,
    val totalStars: Int,
    val rank: Int,
    val description: String?,
    val language: String?,
    val languageColor: String?,
    val repositoryName: String,
    val url: String?,
    val username: String?,
    val since: String?,
    val repositoriesTypeEntity: RepositoriesTypeEntity
)
