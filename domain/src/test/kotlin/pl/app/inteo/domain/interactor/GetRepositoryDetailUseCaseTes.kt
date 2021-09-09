package pl.app.inteo.domain.interactor

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.app.inteo.domain.fakes.FakeData
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.repository.RepositoriesRepository
import pl.app.inteo.domain.utils.DomainBaseTest
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetRepositoryDetailUseCaseTes : DomainBaseTest() {

    @Mock
    lateinit var repositoriesRepository: RepositoriesRepository

    lateinit var gdc: GetRepositoryDetailUseCase

    @Before
    fun setUp() {
        gdc = GetRepositoryDetailUseCase(repositoriesRepository)
    }

    @Test
    fun `should return repository`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(
                repositoriesRepository.getRepository(
                    RepositoriesType.DAILY,
                    "repositoryname1"
                )
            ) doReturn flow { emit(FakeData.getRepository()) }

            // Act (When)
            val item = gdc(RepositoriesType.DAILY, "repositoryname1").single()

            // Assert (Then)
            assertEquals(item.repositoryName, "repositoryname1")
            verify(repositoriesRepository, times(1)).getRepository(RepositoriesType.DAILY, "repositoryname1")
        }


    @Test
    fun `should return exception`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(
                repositoriesRepository.getRepository(
                    RepositoriesType.DAILY,
                    "repositoryname1"
                )
            ) doAnswer { throw IOException() }

            // Act (When)
            launch(exceptionHandler) { gdc(RepositoriesType.DAILY, "repositoryname1").single() }

            // Assert (Then)
            MatcherAssert.assertThat(
                exceptionHandler.uncaughtExceptions.first(),
                CoreMatchers.instanceOf(IOException::class.java)
            )
            verify(repositoriesRepository, times(1)).getRepository(RepositoriesType.DAILY, "repositoryname1")
        }


}
