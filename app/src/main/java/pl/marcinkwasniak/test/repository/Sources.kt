package pl.marcinkwasniak.test.repository

import io.reactivex.Observable
import pl.marcinkwasniak.test.domain.SongsService
import pl.marcinkwasniak.test.domain.remote.Result
import pl.marcinkwasniak.test.model.Song
import pl.marcinkwasniak.test.model.song

/**
 * Created by marcin.kwasniak on 04/04/2019
 */
interface SongSource {
    fun getData(query: String): Observable<Song>
}

class LocalSongSource(private val service: SongsService) : SongSource {
    override fun getData(query: String): Observable<Song> =
        service
            .createLocal()
            .songs()
            .flattenAsObservable { items -> items }
}

class RemoteSongSource(private val service: SongsService) : SongSource {
    override fun getData(query: String): Observable<Song> =
        service
            .createRemote()
            .songs(query)
            .flattenAsObservable { items -> items.results }
            .map { result -> toCommonModel(result) }

    private fun toCommonModel(result: Result): Song =
        song {
            title = result.trackName
            artist = result.artistName
            releaseYear = result.releaseDate.slice(0..3)
        }
}

class MergedSongSource(private val local: SongSource, private val remote: SongSource) : SongSource {
    override fun getData(query: String): Observable<Song> =
        local.getData(query).mergeWith(remote.getData(query))
}

class NoneSongSource : SongSource {
    override fun getData(query: String): Observable<Song> =
        Observable.just(
            song {
                title = "Search for: \"$query\" ignored. No data sources."
                artist = "Try to change network settings."
                releaseYear = ""
            }
        )
}