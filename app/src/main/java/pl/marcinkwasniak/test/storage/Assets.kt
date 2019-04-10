package pl.marcinkwasniak.test.storage

import android.content.res.AssetManager
import org.apache.commons.io.IOUtils

/**
 * Created by marcin.kwasniak on 01/04/2019
 */

fun AssetManager.fileFromAssets(fileName: String): ByteArray? =
    open(fileName).use { inputStream -> IOUtils.toByteArray(inputStream) }
