package pl.app.inteo.domain.models

data class Repository(
    val forks: Int,
    val starsSince: Int,
    val builtBy: List<BuiltBy>,
    val totalStars: Int,
    val rank: Int,
    val description: String?,
    val language: String?,
    val languageColor: String?,
    val repositoryName: String,
    val url: String?,
    val username: String?,
    val since: String?,
    val repositoriesType: RepositoriesType
)
