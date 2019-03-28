package com.tpo_hr.tpohr.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxObservable {
    fun <T> wrapAsync(observable: Observable<T>, scheduler: Scheduler = Schedulers.io()): Observable<T> {
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun wrapAsync(observable: Completable, scheduler: Scheduler = Schedulers.io()): Completable {
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> wrapAsync(observable: Single<T>, scheduler: Scheduler = Schedulers.io()): Single<T> {
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> wrapAsync(observable: Flowable<T>, scheduler: Scheduler = Schedulers.io()): Flowable<T> {
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
    }
}