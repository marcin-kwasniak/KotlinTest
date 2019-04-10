package pl.marcinkwasniak.test.repository

import io.reactivex.Observable
import io.reactivex.Single
import pl.marcinkwasniak.test.domain.NetworkType
import pl.marcinkwasniak.test.model.Song

/**
 * Created by marcin.kwasniak on 04/04/2019
 */
interface SongsRepository {
    fun getValidatedSongs(query: String, networkType: NetworkType): Single<List<Song>>
    fun getAllSongs(query: String, networkType: NetworkType): Observable<Song>
}

class AppSongsRepository(private val directory: SourcesDirectory) : SongsRepository {

    override fun getValidatedSongs(query: String, networkType: NetworkType): Single<List<Song>> =
        getAllSongs(query, networkType)
            .filter { song -> song.toString().contains(query, true) }
            .toSortedList()

    override fun getAllSongs(query: String, networkType: NetworkType): Observable<Song> =
        directory.getSource(
            when (networkType) {
                NetworkType.Local -> SourcesDirectory.Type.Local
                NetworkType.Remote -> SourcesDirectory.Type.Remote
                NetworkType.Both -> SourcesDirectory.Type.Merged
                NetworkType.None -> SourcesDirectory.Type.None
            }
        ).getData(query)
}