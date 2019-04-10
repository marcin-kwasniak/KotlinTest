package pl.marcinkwasniak.test.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by marcin.kwasniak on 03/04/2019
 */
interface AppSchedulers {
    fun io(): Scheduler
    fun main(): Scheduler
}

class NativeSchedulers : AppSchedulers {

    override fun io(): Scheduler = Schedulers.io()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

}