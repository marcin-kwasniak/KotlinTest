package pl.marcinkwasniak.test.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.orhanobut.hawk.Hawk
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.marcinkwasniak.test.R
import pl.marcinkwasniak.test.domain.NetworkType
import pl.marcinkwasniak.test.rx.AppSchedulers
import pl.marcinkwasniak.test.rx.ioToMainThread
import pl.marcinkwasniak.test.storage.Shared
import pl.marcinkwasniak.test.ui.BaseFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by marcin.kwasniak on 03/04/2019
 */
class SearchSongFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SongAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var input: EditText

    private var editTextDisposable: Disposable? = null
    private val schedulers: AppSchedulers by inject()
    private val searchSongViewModel: SearchSongViewModel by viewModel()

    private var viewState: SearchSongViewModel.ViewStateType = SearchSongViewModel.ViewStateType.Content
        set(value) {
            field = value
            when (value) {
                SearchSongViewModel.ViewStateType.Loading -> {
                    progress.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                SearchSongViewModel.ViewStateType.Content -> {
                    progress.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.content_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = initView()

    override fun onDestroyView() {
        super.onDestroyView()
        searchSongViewModel.dispose()
        editTextDisposable?.dispose()
    }

    private fun initView() {
        searchSongViewModel.songs.observe(this, Observer(::onSongsResult))
        searchSongViewModel.state.observe(this, Observer(::onStateChanged))
        input = search_input
        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = SongAdapter(mutableListOf())
        recyclerView = songsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        initTextWatcher()
    }

    private fun initTextWatcher() {
        editTextDisposable = RxTextView.textChanges(search_input)
            .debounce(200, TimeUnit.MILLISECONDS)
            .filter { canSearch(it.toString()) }
            .ioToMainThread(schedulers)
            .subscribeBy(
                onNext = {
                    Timber.d("test textwatcher: $it, size ${it.length}")
                    searchSongViewModel
                        .beginSearch(
                            it.toString(), NetworkType.valueOf(Hawk.get(Shared.NETWORK_TYPE_KEY, intArrayOf(0)))
                        )
                }
            )
    }

    override fun onNetworkChanged(rawType: IntArray) {
        showNetworkInfo(NetworkType.valueOf(rawType))
        if (canSearch(input.text.toString())) {
            searchSongViewModel.beginSearch(input.text.toString(), NetworkType.valueOf(rawType))
        }
    }

    private fun canSearch(query: String): Boolean = query.length > 2

    private fun showNetworkInfo(type: NetworkType) =
        Toast.makeText(context, getString(type.textId), Toast.LENGTH_LONG).show()

    private fun onSongsResult(songs: List<SongViewModel>) = viewAdapter.update(songs)

    private fun onStateChanged(state: SearchSongViewModel.ViewStateType) {
        viewState = state
    }
}