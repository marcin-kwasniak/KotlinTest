package pl.marcinkwasniak.test

import android.app.Application
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.marcinkwasniak.test.di.appModule
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by marcin.kwasniak on 31/03/2019
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initHawk()
        Hawk.init(this).build()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private fun initTimber() =
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            // CrashReportingTree
        }

    private fun initHawk() = Hawk.init(this).build()

}