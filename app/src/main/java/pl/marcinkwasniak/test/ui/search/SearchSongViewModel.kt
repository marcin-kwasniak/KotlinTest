package pl.marcinkwasniak.test.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinkwasniak.test.domain.NetworkType
import pl.marcinkwasniak.test.model.Song
import pl.marcinkwasniak.test.repository.SongsRepository
import pl.marcinkwasniak.test.rx.AppSchedulers
import pl.marcinkwasniak.test.rx.ioToMainThread

/**
 * Created by marcin.kwasniak on 04/04/2019
 */
class SearchSongViewModel(
    private val repository: SongsRepository,
    private val schedulers: AppSchedulers
) : ViewModel() {

    enum class ViewStateType { Loading, Content }

    val songs = MutableLiveData<List<SongViewModel>>()
    val state = MutableLiveData<ViewStateType>()

    private var searchDisposable: Disposable? = null
    private var lastQuery = ""
    private var lastNetworkType = NetworkType.Local

    fun beginSearch(query: String, networkType: NetworkType) {
        if ((lastQuery == query) && (lastNetworkType == networkType))
            return

        lastQuery = query
        lastNetworkType = networkType

        state.value = ViewStateType.Loading
        searchDisposable = repository
            .getValidatedSongs(query, networkType)
            .ioToMainThread(schedulers)
            .subscribeBy(
                onSuccess = { showResult(it) },
                onError = { showError() }
            )
    }

    fun dispose() = searchDisposable?.dispose()

    private fun showResult(result: List<Song>) {
        state.value = ViewStateType.Content
        songs.value = toViewModel(result)
    }

    private fun showError() {
        state.value = ViewStateType.Content
    }

    private fun toViewModel(songs: List<Song>): List<SongViewModel> =
        songs.map { SongViewModel(it.title, it.artist, it.releaseYear) }.toList()
}