package pl.marcinkwasniak.test

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.qualifier.named
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.testng.asserts.SoftAssert
import pl.marcinkwasniak.test.repository.SongSource

/**
 * Created by marcin.kwasniak on 05/04/2019
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class LocalSourceTest : AutoCloseKoinTest() {

    val localSource: SongSource by inject(named("local"))

    @Test
    fun getDataTest() {
        // Arrange
        val query = "Michael"
        val expectedSize = 2229

        // Act
        val songs = localSource.getData(query).toList().blockingGet()

        // Assert
        val assertions = SoftAssert() // alternative for junit5 assertAll method
        assertions.assertNotNull(songs)
        assertions.assertTrue(songs.isNotEmpty(), "empty songs list")
        assertions.assertTrue(songs.size == expectedSize, "songs list is not $expectedSize but ${songs.size}")
        assertions.assertAll()
    }
}