package pl.app.inteo.remote.models

data class RepositoryRemoteModel(
    val forks: Int = 0,
    val starsSince: Int = 0,
    val builtBy: List<RepositoryBuiltByRemoteModel> = emptyList(),
    val totalStars: Int = 0,
    val rank: Int = 0,
    val description: String?,
    val language: String?,
    val languageColor: String?,
    val repositoryName: String,
    val url: String?,
    val username: String?,
    val since: String?
)