package pl.app.inteo.extenstions

import android.view.MenuItem
import androidx.annotation.StringRes
import pl.app.inteo.R
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.model.SortingTypeUiModel

fun MenuItem.mapToPresentation(): RepositoriesTypeUiModel = when (itemId) {
    R.id.menu_today -> RepositoriesTypeUiModel.DAILY
    R.id.menu_week -> RepositoriesTypeUiModel.WEEKLY
    R.id.menu_month -> RepositoriesTypeUiModel.MONTHLY
    else -> RepositoriesTypeUiModel.UNKNOWN
}

fun MenuItem.mapSortingToPresentation(): SortingTypeUiModel = when (itemId) {
    R.id.menu_sort_stars_since -> SortingTypeUiModel.SINCE_STARS
    R.id.menu_sort_total_stars -> SortingTypeUiModel.TOTAL_STARS
    R.id.menu_sort_fork -> SortingTypeUiModel.FORK_COUNT
    else -> SortingTypeUiModel.NO_SORTING
}

@StringRes
fun RepositoriesTypeUiModel.getStarsSinceLabel() = when (this) {
    RepositoriesTypeUiModel.DAILY -> R.string.stars_since_today
    RepositoriesTypeUiModel.WEEKLY -> R.string.stars_since_week
    RepositoriesTypeUiModel.MONTHLY -> R.string.stars_since_month
    else -> R.string.stars_since_today
}
