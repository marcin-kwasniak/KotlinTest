package pl.marcinkwasniak.test.domain.local

import okhttp3.*
import pl.marcinkwasniak.test.storage.LocalStorage
import java.io.IOException


/**
 * Created by marcin.kwasniak on 31/03/2019
 */
class LocalInterceptor(private val storage: LocalStorage) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = localResponse(chain.request())

    private fun localResponse(request: Request): Response = Response
        .Builder()
        .code(200)
        .message("OK")
        .request(request)
        .protocol(Protocol.HTTP_1_1)
        .body(ResponseBody.create(MediaType.parse("application/json"), localData()))
        .addHeader("content-type", "application/json")
        .build()

    private fun localData(): ByteArray = storage.fileFromLocalStorage("songs-list.json") ?: "".toByteArray()

}