package pl.marcinkwasniak.test

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pl.marcinkwasniak.test.storage.LocalStorage

/**
 * Created by marcin.kwasniak on 05/04/2019
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class LocalStorageTest : AutoCloseKoinTest() {

    val localStorage: LocalStorage by inject()

    @Test
    fun getDataTest() {
        // Arrange
        val file = "songs-list.json"
        val expectedSize = 502387

        // Act
        val jsonData = localStorage.fileFromLocalStorage(file)

        // Assert
        assertThat(jsonData).isNotEmpty()
        assertThat(jsonData?.size).isEqualTo(expectedSize)
    }
}