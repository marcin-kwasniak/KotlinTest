package pl.marcinkwasniak.test.domain

import okhttp3.OkHttpClient
import pl.marcinkwasniak.test.domain.local.LocalInterceptor
import pl.marcinkwasniak.test.domain.local.LocalService
import pl.marcinkwasniak.test.domain.remote.iTunesService
import pl.marcinkwasniak.test.storage.LocalStorage
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by marcin.kwasniak on 31/03/2019
 */
interface SongsService {
    fun createLocal(): LocalService
    fun createRemote(): iTunesService
}

class AppSongsService(private val storage: LocalStorage) : SongsService {
    companion object {
        private const val LOCAL_URL = "https://localhost/"
        private const val REMOTE_URL = "https://itunes.apple.com/"

        private fun localClient(storage: LocalStorage): OkHttpClient =
            OkHttpClient.Builder().addInterceptor(LocalInterceptor(storage)).build()
    }

    override fun createLocal(): LocalService = Retrofit
        .Builder()
        .client(localClient(storage))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(LOCAL_URL)
        .build().create(LocalService::class.java)

    override fun createRemote(): iTunesService = Retrofit
        .Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(REMOTE_URL)
        .build()
        .create(iTunesService::class.java)
}
