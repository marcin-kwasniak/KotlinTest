package pl.marcinkwasniak.test.storage

import android.content.res.AssetManager

/**
 * Created by marcin.kwasniak on 03/04/2019
 */
class AssetsStorage(private val assets: AssetManager) : LocalStorage {

    override fun fileFromLocalStorage(file: String): ByteArray? = assets.fileFromAssets(file)

}