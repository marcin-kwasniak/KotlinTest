package pl.marcinkwasniak.test

import android.content.Context
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pl.marcinkwasniak.test.domain.NetworkType

/**
 * Created by marcin.kwasniak on 05/04/2019
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class NetworkTypeTest : AutoCloseKoinTest() {

    val context: Context by inject()

    @Test
    fun localNetworkType() {
        // Arrange
        val typeTextId = NetworkType.Local.textId
        val expected = "Local"

        // Act
        val text = context.resources.getString(typeTextId)

        // Assert
        assertThat(text).isEqualTo(expected)
    }

    @Test
    fun remoteNetworkType() {
        // Arrange
        val typeTextId = NetworkType.Remote.textId
        val expected = "Remote"

        // Act
        val text = context.resources.getString(typeTextId)

        // Assert
        assertThat(text).isEqualTo(expected)
    }

    @Test
    fun bothNetworkType() {
        // Arrange
        val typeTextId = NetworkType.Both.textId
        val expected = "Local and Remote"

        // Act
        val text = context.resources.getString(typeTextId)

        // Assert
        assertThat(text).isEqualTo(expected)
    }

    @Test
    fun noneNetworkType() {
        // Arrange
        val typeTextId = NetworkType.None.textId
        val expected = "None"

        // Act
        val text = context.resources.getString(typeTextId)

        // Assert
        assertThat(text).isEqualTo(expected)
    }
}