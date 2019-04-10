package pl.marcinkwasniak.test.domain.local

import io.reactivex.Single
import pl.marcinkwasniak.test.model.Song
import retrofit2.http.GET

/**
 * Created by marcin.kwasniak on 31/03/2019
 */
interface LocalService {

    @GET("songs-list")
    fun songs(): Single<List<Song>>
}