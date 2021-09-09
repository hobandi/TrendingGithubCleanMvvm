package pl.app.inteo.cache.fakes

import pl.app.inteo.cache.models.BuiltByCacheEntity
import pl.app.inteo.cache.models.RepositoryCacheEntity

object FakeCacheData {

    fun getRepositories(): List<RepositoryCacheEntity> = listOf(
        RepositoryCacheEntity(
            "repositoryname1",
            200,
            500,
            listOf(
                BuiltByCacheEntity(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ), BuiltByCacheEntity(
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
            "https://github.com/username1/repositoryname1",
            "username1",
            "daily",
            1
        ),
        RepositoryCacheEntity(
            "repositoryname2",
            200,
            500,
            listOf(
                BuiltByCacheEntity(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ), BuiltByCacheEntity(
                    "https://avatars.githubusercontent.com/u/43348292?s=40&v=4",
                    "https://github.com/user2",
                    "user2"
                )
            ),
            500,
            1,
            "description2",
            "java",
            "#1234567",
            "https://github.com/username2/repositoryname2",
            "username2",
            "daily",
            1
        ),
    )

    fun getRepository() = RepositoryCacheEntity(
        "repositoryname1",
        200,
        500,
        listOf(
            BuiltByCacheEntity(
                "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                "https://github.com/user1",
                "user1"
            ), BuiltByCacheEntity(
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
        "https://github.com/username1/repositoryname1",
        "username1",
        "daily",
        1
    )
}
