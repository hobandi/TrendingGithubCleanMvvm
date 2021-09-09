package pl.app.inteo.cache.utils

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import pl.app.inteo.cache.dao.BookmarkedDao
import pl.app.inteo.cache.dao.RepositoryDao
import pl.app.inteo.cache.database.CacheDatabase
import java.io.IOException

@ExperimentalCoroutinesApi
abstract class CacheBaseTest {

    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @get:Rule
    val testRule = CoroutineTestRule()

    val dispatcher = testRule.dispatcher
    val exceptionHandler = TestCoroutineExceptionHandler()
    private lateinit var database: CacheDatabase
    protected lateinit var repositoryDao: RepositoryDao
    protected lateinit var bookmarkedDao: BookmarkedDao
    protected lateinit var context: Context

    @Before
    open fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, CacheDatabase::class.java)
            .allowMainThreadQueries().build()
        repositoryDao = database.repositoryDao()
        bookmarkedDao = database.bookmarkedDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        dispatcher.runBlockingTest {
            database.clearAllTables()
        }
        database.close()
    }
}
