package pl.app.inteo.data.source

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import pl.app.inteo.data.fakes.FakeRepositoryEntity
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.repository.RepositoriesCache
import pl.app.inteo.data.utils.DataBaseTest
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryCacheDataSourceTest : DataBaseTest() {

    @Mock
    lateinit var repositoriesCache: RepositoriesCache

    lateinit var rcds: RepositoryCacheDataSource

    @Before
    fun setUp() {
        rcds = RepositoryCacheDataSource(repositoriesCache)
    }

    @Test
    fun `get repositories should return daily list of repositories`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.getRepositories()) doReturn FakeRepositoryEntity.getRepositories()

        val repositories = rcds.getRepositories(RepositoriesTypeEntity.DAILY)

        assertEquals(repositories[0].repositoriesTypeEntity, RepositoriesTypeEntity.DAILY)
        verify(repositoriesCache, times(1)).getRepositories()
    }

    @Test
    fun `get repositories should return list of repositories`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.getRepositories()) doReturn FakeRepositoryEntity.getRepositories()

        val repositories = rcds.getRepositories(RepositoriesTypeEntity.DAILY)

        assertEquals(repositories.size, 2)
        verify(repositoriesCache, times(1)).getRepositories()
    }

    @Test
    fun `get repositories should return return error`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.getRepositories()) doAnswer { throw IOException() }

        launch(exceptionHandler) { rcds.getRepositories(RepositoriesTypeEntity.DAILY) }

        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(), CoreMatchers.instanceOf(IOException::class.java)
        )

        verify(repositoriesCache, times(1)).getRepositories()
    }

    @Test
    fun `save repositories should save repositories into local cache `() = dispatcher.runBlockingTest {
        val repositories = rcds.getRepositories(RepositoriesTypeEntity.DAILY)

        rcds.saveRepositories(RepositoriesTypeEntity.DAILY, repositories)

        verify(repositoriesCache, times(1)).saveRepositories(RepositoriesTypeEntity.DAILY, repositories)
        verify(repositoriesCache, times(1)).setLastCacheTime(any())
    }

    @Test
    fun `save repositories should return error failed to save last time`() = dispatcher.runBlockingTest {
        val repositories = FakeRepositoryEntity.getRepositories()
        whenever(
            repositoriesCache.saveRepositories(
                RepositoriesTypeEntity.DAILY,
                repositories
            )
        ) doAnswer { throw IOException() }

        launch(exceptionHandler) { rcds.saveRepositories(RepositoriesTypeEntity.DAILY, repositories) }

        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(), CoreMatchers.instanceOf(IOException::class.java)
        )
        verify(repositoriesCache, times(1)).saveRepositories(RepositoriesTypeEntity.DAILY, repositories)
        verify(repositoriesCache, times(0)).setLastCacheTime(any())
    }


    @Test
    fun `get bookmarked should return bookmarked repositories`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.getBookMarkedRepositories(RepositoriesTypeEntity.DAILY)) doReturn FakeRepositoryEntity.getRepositories()

        val bookmarkedRepositories = repositoriesCache.getBookMarkedRepositories(RepositoriesTypeEntity.DAILY)

        assertEquals(bookmarkedRepositories.size, 2)
        verify(repositoriesCache, times(1)).getBookMarkedRepositories(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `is bookmarked should return status`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.isBookmarked("test")) doReturn true

        val isBookmarked = rcds.isBookmarked("test")

        assertEquals(isBookmarked, true)
        verify(repositoriesCache, times(1)).isBookmarked("test")
    }

    @Test
    fun `set bookmark repository should return success`() = dispatcher.runBlockingTest {
        // Arrange (Given)
        val repositoryName = "test"
        val statusFake = 1
        whenever(repositoriesCache.setRepositoryBookmarked(repositoryName, true)) doReturn statusFake

        val resultStatus = rcds.setRepositoryBookmarked(repositoryName, true)

        assertEquals(resultStatus, statusFake)
        verify(repositoriesCache, times(1)).setRepositoryBookmarked("test", true)
    }

    @Test
    fun `clear repositories should clear`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.clearRepositories()) doReturn 1

        val value = rcds.clearRepositories()

        assertEquals(value, 1)
        verify(repositoriesCache, times(1)).clearRepositories()
    }

    @Test
    fun `set last cache time should set last time`() = dispatcher.runBlockingTest {
        whenever(repositoriesCache.isCached()) doReturn true

        val resultStatus = rcds.isCached()

        assertTrue(resultStatus)
        verify(repositoriesCache, times(1)).isCached()
    }

    @Test
    fun `is cached should return true`() = dispatcher.runBlockingTest {
        `when`(repositoriesCache.isExpired()) doReturn true

        val resultStatus = rcds.isExpired()

        assertTrue(resultStatus)
        verify(repositoriesCache, times(1)).isExpired()
    }
}
