package pl.app.inteo.data.source

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
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
import org.mockito.junit.MockitoJUnitRunner
import pl.app.inteo.data.fakes.FakeRepositoryEntity
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.repository.RepositoriesRemote
import pl.app.inteo.data.utils.DataBaseTest
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryRemoteDataSourceTest : DataBaseTest() {

    @Mock
    lateinit var repositoriesRemote: RepositoriesRemote

    lateinit var rrds: RepositoryRemoteDataSource

    @Before
    fun setUp() {
        rrds = RepositoryRemoteDataSource(repositoriesRemote)
    }

    @Test
    fun `get repositories should return daily list of repositories`() = dispatcher.runBlockingTest {
        whenever(repositoriesRemote.getRepository(RepositoriesTypeEntity.DAILY)) doReturn FakeRepositoryEntity.getRepositories()

        val repositories = rrds.getRepositories(RepositoriesTypeEntity.DAILY)

        assertEquals(repositories[0].repositoriesTypeEntity, RepositoriesTypeEntity.DAILY)
        verify(repositoriesRemote, times(1)).getRepository(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `get repositories should return list of repositories`() = dispatcher.runBlockingTest {
        whenever(repositoriesRemote.getRepository(RepositoriesTypeEntity.DAILY)) doReturn FakeRepositoryEntity.getRepositories()

        val repositories = rrds.getRepositories(RepositoriesTypeEntity.DAILY)

        assertEquals(repositories.size, 2)
        verify(repositoriesRemote, times(1)).getRepository(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `get repositories should return return error`() = dispatcher.runBlockingTest {
        whenever(repositoriesRemote.getRepository(RepositoriesTypeEntity.DAILY)) doAnswer { throw IOException() }

        launch(exceptionHandler) { rrds.getRepositories(RepositoriesTypeEntity.DAILY) }

        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(), CoreMatchers.instanceOf(IOException::class.java)
        )

        verify(repositoriesRemote, times(1)).getRepository(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `save repositories should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.saveRepositories(RepositoriesTypeEntity.UNKNOWN, emptyList()) }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }


    @Test
    fun `get bookmarked should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.getBookMarkedRepositories(RepositoriesTypeEntity.UNKNOWN) }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }

    @Test
    fun `is bookmarked should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.isBookmarked("") }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }

    @Test
    fun `set bookmark repository should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.getBookMarkedRepositories(RepositoriesTypeEntity.UNKNOWN) }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }

    @Test
    fun `clear repositories should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.clearRepositories() }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }

    @Test
    fun `set last cache time should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.setLastCacheTime(0L) }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }

    @Test
    fun `is cached should return error - not supported by remote`() = dispatcher.runBlockingTest {
        launch(exceptionHandler) { rrds.isCached() }

        // Assert (Then)
        MatcherAssert.assertThat(
            exceptionHandler.uncaughtExceptions.first(),
            CoreMatchers.instanceOf(UnsupportedOperationException::class.java)
        )
    }
}
