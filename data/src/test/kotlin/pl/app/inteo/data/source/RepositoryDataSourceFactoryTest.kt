package pl.app.inteo.data.source

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import pl.app.inteo.data.utils.DataBaseTest

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryDataSourceFactoryTest : DataBaseTest() {

    @Mock
    lateinit var cacheDataStore: RepositoryCacheDataSource

    @Mock
    lateinit var remoteDataStore: RepositoryRemoteDataSource

    lateinit var rdsf: RepositoryDataSourceFactory

    @Before
    fun setUp() {
        rdsf = RepositoryDataSourceFactory(cacheDataStore, remoteDataStore)
    }

    @Test
    fun `get data store with cache should return repository from cache data-source`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(cacheDataStore.isExpired()) doReturn false
            whenever(cacheDataStore.isCached()) doReturn true
            // Act (When)
            val dataSource = rdsf.getDataSource()
            // Assert (Then)
            assertThat(dataSource, instanceOf(RepositoryCacheDataSource::class.java))
            verify(cacheDataStore, Mockito.times(1)).isExpired()
        }

    @Test
    fun `get data store with cache should return repository from remote data-source`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(cacheDataStore.isExpired()) doReturn true
            whenever(cacheDataStore.isCached()) doReturn true
            // Act (When)
            val dataSource = rdsf.getDataSource()
            // Assert (Then)
            assertThat(dataSource, instanceOf(RepositoryRemoteDataSource::class.java))
            verify(cacheDataStore, Mockito.times(1)).isExpired()
        }
}
