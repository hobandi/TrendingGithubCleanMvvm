package pl.app.inteo.cache

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pl.app.inteo.cache.utils.CacheBaseTest

@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class RepositoriesCacheImpTest : CacheBaseTest()
