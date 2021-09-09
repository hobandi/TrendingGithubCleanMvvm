package dependencies

object UiDep {

    // Kotlin
    const val kotlin = Dependencies.KotlinDep.kotlin

    // Core
    const val coreKtx = Dependencies.CoreDep.coreKtx
    const val appCompat = Dependencies.CoreDep.appCompat
    const val swipeToRefresh = Dependencies.CoreDep.swipeToRefresh
    const val material = Dependencies.CoreDep.material
    const val constraint = Dependencies.CoreDep.constraint
    const val navigationFragmentKtx = Dependencies.CoreDep.navigationFragmentKtx
    const val navigationUiKtx = Dependencies.CoreDep.navigationUiKtx
    const val activityKtx = Dependencies.CoreDep.activityKtx

    // LifeCycle
    val LifeCycle = listOf(
        Dependencies.LifeCycleDep.viewModelKtx,
        Dependencies.LifeCycleDep.lifeCycleExtension,
        Dependencies.LifeCycleDep.lifeCycleRuntime,
        Dependencies.LifeCycleDep.lifeCycleRuntimeKtx
    )

    // Koin
    val koin = listOf(
        Dependencies.KoinDep.core,
        Dependencies.KoinDep.android
    )

    val koinTest = listOf(
        Dependencies.KoinTestDep.junit4,
        Dependencies.KoinTestDep.test
    )

    // Coroutines
    val Coroutines = listOf(
        Dependencies.CoroutinesDep.coroutineCore,
        Dependencies.CoroutinesDep.coroutineAndroid
    )

    const val glide = Dependencies.GlideDep.glide
    const val shimmer = Dependencies.FacebookDep.shimmer
    const val glideKapt = Dependencies.GlideDep.glideKapt
    const val moshi = Dependencies.RetrofitDep.moshiConverter
    const val timber = Dependencies.TimberDep.timber

    object Test {
        const val junit = Dependencies.TestDep.junit
        const val coroutines = Dependencies.TestDep.coroutinesTest
        const val mockitoKotlin = Dependencies.TestDep.mockitoKotlin
        const val mockitoInline = Dependencies.TestDep.mockitoInline
        const val assertJ = Dependencies.TestDep.assertJ
        const val androidxArchCore = Dependencies.TestDep.androidxArchCore
        const val robolectric = Dependencies.TestDep.robolectric
        const val testExtJunit = Dependencies.TestDep.testExtJunit
    }
}