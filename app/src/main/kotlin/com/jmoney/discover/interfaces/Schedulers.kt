package com.jmoney.discover.interfaces

import io.reactivex.Scheduler

interface Schedulers {
    fun mainThread() : Scheduler

    fun io() : Scheduler

    fun computation() : Scheduler
}