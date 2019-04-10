package pl.marcinkwasniak.test.rx

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by marcin.kwasniak on 03/04/2019
 */

fun <T> Single<T>.ioToMainThread(schedulers: AppSchedulers): Single<T> = subscribeOn(schedulers.io()).observeOn(schedulers.main())
fun <T> Observable<T>.ioToMainThread(schedulers: AppSchedulers): Observable<T> = subscribeOn(schedulers.io()).observeOn(schedulers.main())