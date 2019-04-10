package pl.marcinkwasniak.test

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pl.marcinkwasniak.test.repository.*

/**
 * Created by marcin.kwasniak on 05/04/2019
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class SourcesDirectoryTest : AutoCloseKoinTest() {

    val directory: SourcesDirectory by inject()

    @Test
    fun localSourceTest() {
        // Arrange
        val type = SourcesDirectory.Type.Local

        // Act
        val source = directory.getSource(type)

        // Assert
        assertThat(source).isInstanceOf(LocalSongSource::class.java)
    }

    @Test
    fun remoteSourceTest() {
        // Arrange
        val type = SourcesDirectory.Type.Remote

        // Act
        val source = directory.getSource(type)

        // Assert
        assertThat(source).isInstanceOf(RemoteSongSource::class.java)
    }

    @Test
    fun mergedSourceTest() {
        // Arrange
        val type = SourcesDirectory.Type.Merged

        // Act
        val source = directory.getSource(type)

        // Assert
        assertThat(source).isInstanceOf(MergedSongSource::class.java)
    }

    @Test
    fun noneSourceTest() {
        // Arrange
        val type = SourcesDirectory.Type.None

        // Act
        val source = directory.getSource(type)

        // Assert
        assertThat(source).isInstanceOf(NoneSongSource::class.java)
    }
}