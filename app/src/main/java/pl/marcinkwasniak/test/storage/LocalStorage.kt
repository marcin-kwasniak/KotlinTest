package pl.marcinkwasniak.test.storage

/**
 * Created by marcin.kwasniak on 03/04/2019
 */
interface LocalStorage {
    fun fileFromLocalStorage(file: String): ByteArray?
}