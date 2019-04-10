package pl.marcinkwasniak.test

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import pl.marcinkwasniak.test.rx.AppSchedulers

/**
 * Created by marcin.kwasniak on 03/04/2019
 */

class TestSchedulers : AppSchedulers {

    override fun io(): Scheduler = Schedulers.io()

    override fun main(): Scheduler = Schedulers.trampoline()

}