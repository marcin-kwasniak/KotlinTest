package pl.marcinkwasniak.test

/**
 * Created by marcin.kwasniak on 05/04/2019
 */

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.TestLifecycle
import com.jraska.livedata.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pl.marcinkwasniak.test.domain.NetworkType
import pl.marcinkwasniak.test.repository.SongsRepository
import pl.marcinkwasniak.test.rx.AppSchedulers
import pl.marcinkwasniak.test.ui.search.SearchSongViewModel
import pl.marcinkwasniak.test.ui.search.SongViewModel

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class SearchSongViewModelTest :AutoCloseKoinTest(){
    @get:Rule val testRule = InstantTaskExecutorRule()

    private val repo : SongsRepository by inject()

    private val schedulers : AppSchedulers by inject()

    private lateinit var viewModel: SearchSongViewModel

    @Before
    fun before() {
        viewModel = SearchSongViewModel(repo, schedulers)
    }

    @Test
    fun localSearchWithResultsTest() {
        // Arrange
        val testObserver = TestObserver.create<List<SongViewModel>>()
        val testLifecycle = TestLifecycle.initialized()
        viewModel.songs.observe(testLifecycle, testObserver)

        // Act
        viewModel.beginSearch("Michael", NetworkType.Local)
        testObserver.assertNoValue()
        testLifecycle.resume()

        // Assert
        testObserver.assertNoValue()
            .awaitValue()
            .assertHasValue()
            .assertNever { it.isEmpty() }

        viewModel.songs.removeObserver(testObserver)
    }


    @Test
    fun localSearchWithNoResultsTest() {
        // Arrange
        val testObserver = TestObserver.create<List<SongViewModel>>()
        val testLifecycle = TestLifecycle.initialized()
        viewModel.songs.observe(testLifecycle, testObserver)

        // Act
        viewModel.beginSearch("$%GT#FDF#GFTD", NetworkType.Local)
        testObserver.assertNoValue()
        testLifecycle.resume()

        // Assert
        testObserver.assertNoValue()
            .awaitValue()
            .assertHasValue()
            .assertNever { it.isNotEmpty() }

        viewModel.songs.removeObserver(testObserver)
    }
    
}