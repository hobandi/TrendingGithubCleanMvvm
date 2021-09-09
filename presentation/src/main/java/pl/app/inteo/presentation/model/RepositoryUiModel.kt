package pl.app.inteo.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryUiModel(
    val forks: Int,
    val starsSince: Int,
    val builtBy: List<BuiltByUiModel>,
    val totalStars: Int,
    val rank: Int,
    val description: String?,
    val technology: String?,
    val languageColor: String?,
    val repositoryName: String,
    val url: String?,
    val username: String?,
    val since: String?,
    val repositoryTypeUiModel: RepositoriesTypeUiModel
) : Parcelable