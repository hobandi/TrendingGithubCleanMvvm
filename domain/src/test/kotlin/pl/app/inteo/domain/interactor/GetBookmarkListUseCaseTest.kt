package pl.app.inteo.domain.interactor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.app.inteo.domain.fakes.FakeData
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.repository.RepositoriesRepository
import pl.app.inteo.domain.utils.DomainBaseTest

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetBookmarkListUseCaseTest : DomainBaseTest() {

    @Mock
    lateinit var repositoriesRepository: RepositoriesRepository

    lateinit var gbc: GetBookmarkListUseCase

    @Before
    fun setUp() {
        gbc = GetBookmarkListUseCase(repositoriesRepository)
    }

    @Test
    fun `should return bookmark list`() = dispatcher.runBlockingTest {
        val list = FakeData.getRepositories()
        whenever(repositoriesRepository.getBookMarkedRepositories(RepositoriesType.DAILY)) doReturn flow { emit(list) }

        val result = gbc(GetBookmarkListUseCase.GetBookmarkListUseCaseData(RepositoriesType.DAILY, false)).single()

        assertEquals(result, list)
        verify(repositoriesRepository, times(1)).getBookMarkedRepositories(RepositoriesType.DAILY)
    }

    @Test
    fun `when force update should request force update and request getRepositories`() = dispatcher.runBlockingTest {
        val list = FakeData.getRepositories()
        whenever(repositoriesRepository.getBookMarkedRepositories(RepositoriesType.DAILY)) doReturn flow { emit(list) }
        whenever(repositoriesRepository.getRepositories(RepositoriesType.DAILY)) doReturn flow { emit(list) }

        gbc(GetBookmarkListUseCase.GetBookmarkListUseCaseData(RepositoriesType.DAILY, true))

        verify(repositoriesRepository, times(1)).requestForceUpdate()
        verify(repositoriesRepository, times(1)).getRepositories(RepositoriesType.DAILY)
    }

}
