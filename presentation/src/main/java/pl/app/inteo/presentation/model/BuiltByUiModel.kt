package pl.app.inteo.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuiltByUiModel(
    val avatar: String?,
    val url: String?,
    val username: String?
) : Parcelable
