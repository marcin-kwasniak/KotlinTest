package pl.marcinkwasniak.test.domain.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by marcin.kwasniak on 31/03/2019
 */
interface iTunesService {

    companion object {
        const val ResultLimit = 25
    }

    @GET("search")
    fun songs(
        @Query("term") query: String,
        @Query("limit") limit: Int = ResultLimit,
        @Query("entity") entity: String = "song"
    ): Single<RemoteResponse>
}