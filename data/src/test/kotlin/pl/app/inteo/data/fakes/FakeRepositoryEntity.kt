package pl.app.inteo.data.fakes

import pl.app.inteo.data.models.BuiltByEntity
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity

object FakeRepositoryEntity {

    fun getRepositories(): List<RepositoryEntity> = listOf(
        RepositoryEntity(
            100,
            200,
            listOf(
                BuiltByEntity(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ),
                BuiltByEntity(
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
            "daily",
            RepositoriesTypeEntity.DAILY
        ),
        RepositoryEntity(
            200,
            100,
            listOf(
                BuiltByEntity(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ),
                BuiltByEntity(
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
            "daily",
            RepositoriesTypeEntity.DAILY
        ),
    )

    fun getRepository() = RepositoryEntity(
        100,
        200,
        listOf(
            BuiltByEntity(
                "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                "https://github.com/user1",
                "user1"
            ),
            BuiltByEntity(
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
        "daily",
        RepositoriesTypeEntity.DAILY
    )
}
