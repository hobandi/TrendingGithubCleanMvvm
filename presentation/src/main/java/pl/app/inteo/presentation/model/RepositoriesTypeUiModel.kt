package pl.app.inteo.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class RepositoriesTypeUiModel : Parcelable {
    DAILY, WEEKLY, MONTHLY, UNKNOWN
}