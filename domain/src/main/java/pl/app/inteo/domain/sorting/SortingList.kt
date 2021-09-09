package pl.app.inteo.domain.sorting

import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.models.SortingType

fun List<Repository>.sort(sortingType: SortingType) = sortedWith(createComparator(sortingType))

fun createComparator(sortingType: SortingType): Comparator<Repository> = when (sortingType) {
    SortingType.NO_SORTING -> Comparator { r: Repository, r2: Repository -> r.rank - r2.rank }
    SortingType.TOTAL_STARS -> Comparator { r: Repository, r2: Repository -> r2.totalStars - r.totalStars }
    SortingType.SINCE_STARS -> Comparator { r: Repository, r2: Repository -> r2.starsSince - r.starsSince }
    SortingType.FORK_COUNT -> Comparator { r: Repository, r2: Repository -> r2.forks - r.forks }
}
