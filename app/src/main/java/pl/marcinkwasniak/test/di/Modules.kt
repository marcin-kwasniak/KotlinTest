package pl.marcinkwasniak.test.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.marcinkwasniak.test.domain.AppSongsService
import pl.marcinkwasniak.test.domain.SongsService
import pl.marcinkwasniak.test.repository.*
import pl.marcinkwasniak.test.rx.AppSchedulers
import pl.marcinkwasniak.test.rx.NativeSchedulers
import pl.marcinkwasniak.test.storage.AssetsStorage
import pl.marcinkwasniak.test.storage.LocalStorage
import pl.marcinkwasniak.test.ui.search.SearchSongViewModel

/**
 * Created by marcin.kwasniak on 04/04/2019
 */
val appModule = module {

    single<LocalStorage> { AssetsStorage(androidContext().assets) }
    single<SongsService> { AppSongsService(get()) }
    single<AppSchedulers> { NativeSchedulers() }

    single<SongSource>(named("local")) { LocalSongSource(get()) }
    single<SongSource>(named("remote")) { RemoteSongSource(get()) }
    single<SongSource>(named("merged")) { MergedSongSource(get(named("local")), get(named("remote"))) }
    single<SongSource>(named("none")) { NoneSongSource() }

    single<SourcesDirectory> {
        AppSourcesDirectory(
            get(named("local")),
            get(named("remote")),
            get(named("merged")),
            get(named("none"))
        )
    }

    single<SongsRepository> {
        AppSongsRepository(
            get()
        )
    }

    viewModel { SearchSongViewModel(get(), get()) }
}