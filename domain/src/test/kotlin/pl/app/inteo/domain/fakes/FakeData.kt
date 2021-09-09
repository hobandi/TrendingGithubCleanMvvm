package pl.app.inteo.domain.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.app.inteo.domain.models.BuiltBy
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.models.SettingType
import pl.app.inteo.domain.models.Settings

object FakeData {

    fun getRepositories(): List<Repository> = listOf(
        Repository(
            100,
            200,
            listOf(
                BuiltBy(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ),
                BuiltBy(
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
            RepositoriesType.DAILY
        ),
        Repository(
            200,
            100,
            listOf(
                BuiltBy(
                    "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                    "https://github.com/user1",
                    "user1"
                ),
                BuiltBy(
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
            RepositoriesType.DAILY
        ),
    )

    fun getRepository() = Repository(
        100,
        200,
        listOf(
            BuiltBy(
                "https://avatars.githubusercontent.com/u/7001608?s=40&v=4",
                "https://github.com/user1",
                "user1"
            ),
            BuiltBy(
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
        RepositoriesType.DAILY
    )

    fun getSettings(isNightMode: Boolean): Flow<List<Settings>> = flow {
        val settings = listOf(
            Settings(1, SettingType.SWITCH, "Theme mode", "", isNightMode),
            Settings(2, SettingType.EMPTY, "Clear cache", ""),
            Settings(2, SettingType.TEXT, "App version", "1.0")
        )
        emit(settings)
    }
}
