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
import pl.app.inteo.domain.repository.RepositoriesRepository
import pl.app.inteo.domain.utils.DomainBaseTest

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetBookmarkUseCaseTest : DomainBaseTest() {

    @Mock
    lateinit var repositoriesRepository: RepositoriesRepository

    lateinit var guc: GetBookmarkUseCase

    @Before
    fun setUp() {
        guc = GetBookmarkUseCase(repositoriesRepository)
    }

    @Test
    fun `is bookmarked should return true`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(repositoriesRepository.isBookmarked("test")) doReturn flow { emit(true) }

            // Act (When)
            val status = guc("test").single()

            // Assert (Then)
            assertEquals(status, true)
            verify(repositoriesRepository, times(1)).isBookmarked("test")
        }

    @Test
    fun `is bookmarked should return false`() =
        dispatcher.runBlockingTest {
            // Arrange (Given)
            whenever(repositoriesRepository.isBookmarked("test")) doReturn flow { emit(false) }

            // Act (When)
            val status = guc("test").single()

            // Assert (Then)
            assertEquals(status, false)
            verify(repositoriesRepository, times(1)).isBookmarked("test")
        }
}
