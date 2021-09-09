package pl.app.inteo.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import pl.app.inteo.data.fakes.FakeRepositoryEntity
import pl.app.inteo.data.mapper.toDomain
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.repository.RepositoryDataSource
import pl.app.inteo.data.source.RepositoryCacheDataSource
import pl.app.inteo.data.source.RepositoryDataSourceFactory
import pl.app.inteo.data.source.RepositoryRemoteDataSource
import pl.app.inteo.data.utils.DataBaseTest
import pl.app.inteo.domain.models.RepositoriesType

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoriesRepositoryImpTest : DataBaseTest() {

    @Mock
    lateinit var dataSource: RepositoryDataSource

    @Mock
    lateinit var dataSourceFactory: RepositoryDataSourceFactory

    lateinit var rri: RepositoriesRepositoryImp

    @Before
    fun setUp() {
        rri = RepositoriesRepositoryImp(dataSourceFactory)
    }

    @Test
    fun `get repositories should return repository liste from cache`() = dispatcher.runBlockingTest {
        whenever(dataSourceFactory.getDataSource()) doReturn dataSource
        whenever(
            dataSourceFactory.getDataSource().getRepositories(RepositoriesTypeEntity.DAILY)
        ) doReturn FakeRepositoryEntity.getRepositories()

        val list = rri.getRepositories(RepositoriesType.DAILY).single()

        assertEquals(list.size, 2)
        verify(dataSourceFactory.getDataSource(), times(1)).getRepositories(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `get repositories should clear data base and save`() = dispatcher.runBlockingTest {
        val remoteDataSource = Mockito.mock(RepositoryRemoteDataSource::class.java)
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getDataSource()) doReturn remoteDataSource
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource
        whenever(
            dataSourceFactory.getDataSource().getRepositories(RepositoriesTypeEntity.DAILY)
        ) doReturn FakeRepositoryEntity.getRepositories()

        val list = rri.getRepositories(RepositoriesType.DAILY).single()

        assertEquals(list.size, 2)
        verify(cashDataSource, times(1)).saveRepositories(any(), any())
        verify(cashDataSource, times(1)).clearRepositories()
        verify(dataSourceFactory.getDataSource(), times(1)).getRepositories(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `get repository should return repository and save`() = dispatcher.runBlockingTest {
        val remoteDataSource = Mockito.mock(RepositoryRemoteDataSource::class.java)
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getDataSource()) doReturn remoteDataSource
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource
        whenever(
            dataSourceFactory.getDataSource().getRepositories(RepositoriesTypeEntity.DAILY)
        ) doReturn FakeRepositoryEntity.getRepositories()

        val item = rri.getRepository(RepositoriesType.DAILY, "repositoryname1").single()

        assertEquals(item.repositoryName, "repositoryname1")
        verify(cashDataSource, times(1)).saveRepositories(any(), any())
        verify(cashDataSource, times(1)).clearRepositories()
        verify(dataSourceFactory.getDataSource(), times(1)).getRepositories(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `save repository should save`() = dispatcher.runBlockingTest {
        val list = listOf(FakeRepositoryEntity.getRepository().toDomain())
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource

        rri.saveRepositories(RepositoriesType.DAILY, list)

        verify(dataSourceFactory.getCacheDataSource(), times(1)).saveRepositories(any(), any())
    }

    @Test
    fun `get bookmarked repositories should call`() = dispatcher.runBlockingTest {
        val data = FakeRepositoryEntity.getRepositories()
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource
        whenever(cashDataSource.getBookMarkedRepositories(RepositoriesTypeEntity.DAILY)) doReturn data
        val list = rri.getBookMarkedRepositories(RepositoriesType.DAILY).single()

        assertEquals(list.size, 2)
        verify(dataSourceFactory.getCacheDataSource(), times(1)).getBookMarkedRepositories(RepositoriesTypeEntity.DAILY)
    }

    @Test
    fun `is bookmarked show return true and call`() = dispatcher.runBlockingTest {
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource
        whenever(dataSourceFactory.getCacheDataSource().isBookmarked("test")) doReturn true

        val value = rri.isBookmarked("test").single()

        assertEquals(value, true)
        verify(dataSourceFactory.getCacheDataSource(), times(1)).isBookmarked("test")
    }

    @Test
    fun `set repository bookmarked should be called`() = dispatcher.runBlockingTest {
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource
        whenever(cashDataSource.setRepositoryBookmarked(any(), any())) doReturn 1

        val value = rri.setRepositoryBookmarked("test", true).single()

        assertEquals(value, 1)
        verify(dataSourceFactory.getCacheDataSource(), times(1)).setRepositoryBookmarked("test", true)
    }

    @Test
    fun `request Force update should set set last cache time`() = dispatcher.runBlockingTest {
        val cashDataSource = Mockito.mock(RepositoryCacheDataSource::class.java)
        whenever(dataSourceFactory.getCacheDataSource()) doReturn cashDataSource

        rri.requestForceUpdate()

        verify(dataSourceFactory.getCacheDataSource(), times(1)).setLastCacheTime(0)
    }
}
