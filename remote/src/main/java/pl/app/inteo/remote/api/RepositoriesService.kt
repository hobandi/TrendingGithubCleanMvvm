package pl.app.inteo.remote.api

import pl.app.inteo.remote.models.RepositoryRemoteModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoriesService {

    @GET("repositories")
    suspend fun getRepositories(@Query("since") since: String): List<RepositoryRemoteModel>
}
