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
class GetRepositoriesUseCaseTest : DomainBaseTest() {
    @Mock
    lateinit var repositoriesRepository: RepositoriesRepository

    lateinit var guc: GetRepositoriesUseCase

    @Before
    fun setUp() {
        guc = GetRepositoriesUseCase(repositoriesRepository)
    }

    @Test
    fun `should return repository list`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(repositoriesRepository.getRepositories(RepositoriesType.DAILY)) doReturn flow { emit(FakeData.getRepositories()) }

            // Act (When)
            val list = guc(GetRepositoriesUseCase.GetRepositoriesUseCaseData(RepositoriesType.DAILY, false)).single()

            // Assert (Then)
            assertEquals(list.size, 2)
            verify(repositoriesRepository, times(1)).getRepositories(RepositoriesType.DAILY)
        }

    @Test
    fun `should return repository list with force update`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(repositoriesRepository.getRepositories(RepositoriesType.DAILY)) doReturn flow { emit(FakeData.getRepositories()) }

            // Act (When)
            val list = guc(GetRepositoriesUseCase.GetRepositoriesUseCaseData(RepositoriesType.DAILY, true)).single()

            // Assert (Then)
            assertEquals(list.size, 2)
            verify(repositoriesRepository, times(1)).requestForceUpdate()
            verify(repositoriesRepository, times(1)).getRepositories(RepositoriesType.DAILY)
        }
}

