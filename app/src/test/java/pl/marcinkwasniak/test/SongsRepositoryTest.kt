package pl.marcinkwasniak.test

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.testng.asserts.SoftAssert
import pl.marcinkwasniak.test.domain.NetworkType
import pl.marcinkwasniak.test.repository.SongsRepository

/**
 * Created by marcin.kwasniak on 05/04/2019
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class SongsRepositoryTest : AutoCloseKoinTest() {

    val service: SongsRepository by inject()

    @Test
    fun validatedSongsTest() {
        // Arrange
        val query = "Michael"
        val networkType = NetworkType.Local
        val expectedSize = 8

        // Act
        val songs = service.getValidatedSongs(query, networkType).blockingGet()

        // Assert
        val assertions = SoftAssert() // alternative for junit5 assertAll method
        assertions.assertNotNull(songs)
        assertions.assertTrue(songs.isNotEmpty(), "empty songs list")
        assertions.assertTrue(songs.size == expectedSize, "songs list is not $expectedSize but ${songs.size}")
        assertions.assertAll()
    }

    /**
     * I know that test with real http connection is not good practise.
     * It's just a showcase that we can actually do it. It can be useful, e.g. to learn a new web api
     * */
    @Test
    fun validatedRemoteSongsTest() {
        // Arrange
        val query = "Michael"
        val networkType = NetworkType.Remote

        // Act
        val songs = service.getValidatedSongs(query, networkType).blockingGet()

        // Assert
        assertThat(songs).isNotEmpty() // Fluent assertions with Truth, unfortunately there is no assertAll method
    }

    @Test
    fun allSongsTest() {
        // Arrange
        val query = "Michael"
        val networkType = NetworkType.Local
        val expectedSize = 2229

        // Act
        val songs = service.getAllSongs(query, networkType)
            .toList()
            .blockingGet()

        // Assert
        val assertions = SoftAssert() // alternative for junit5 assertAll method
        assertions.assertNotNull(songs)
        assertions.assertTrue(songs.isNotEmpty(), "empty songs list")
        assertions.assertTrue(songs.size == expectedSize, "songs list is not $expectedSize but ${songs.size}")
        assertions.assertAll()
    }
}