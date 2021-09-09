package pl.app.inteo.remote.fakes

import pl.app.inteo.remote.models.RepositoryBuiltByRemoteModel
import pl.app.inteo.remote.models.RepositoryRemoteModel

object FakeRemoteData {

    fun getRepositories(): List<RepositoryRemoteModel> = listOf(
        RepositoryRemoteModel(
            100,
            200,
            listOf(
                RepositoryBuiltByRemoteModel(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ),
                RepositoryBuiltByRemoteModel(
                    "https://avatars.githubusercontent.com/u/43348292?s=40&v=4",
                    "https://github.com/user2",
                    "user2"
                )
            ),
            500,
            1,
            "description1",
            "java",
            "#1234567",
            "repositoryname1",
            "https://github.com/username1/repositoryname1",
            "username1",
            "daily"
        ),
        RepositoryRemoteModel(
            200,
            100,
            listOf(
                RepositoryBuiltByRemoteModel(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ),
                RepositoryBuiltByRemoteModel(
                    "https://avatars.githubusercontent.com/u/43348292?s=40&v=4",
                    "https://github.com/user2",
                    "user2"
                )
            ),
            400,
            2,
            "description2",
            "java",
            "#1234567",
            "repositoryname2",
            "https://github.com/username2/repositoryname2",
            "username2",
            "daily"
        ),
    )

    fun getRepository() = RepositoryRemoteModel(
        100,
        200,
        listOf(
            RepositoryBuiltByRemoteModel(
                "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                "https://github.com/user1",
                "user1"
            ),
            RepositoryBuiltByRemoteModel(
                "https://avatars.githubusercontent.com/u/43348292?s=40&v=4",
                "https://github.com/user2",
                "user2"
            )
        ),
        500,
        1,
        "description1",
        "java",
        "#1234567",
        "repositoryname1",
        "https://github.com/username1/repositoryname1",
        "username1",
        "daily"
    )
}
