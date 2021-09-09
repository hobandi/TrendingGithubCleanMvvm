package pl.app.inteo.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class SortingTypeUiModel : Parcelable {
    NO_SORTING, TOTAL_STARS, SINCE_STARS, FORK_COUNT
}
