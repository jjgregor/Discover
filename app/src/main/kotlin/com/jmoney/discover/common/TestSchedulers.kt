package com.jmoney.discover.common

import com.jmoney.discover.interfaces.Schedulers
import io.reactivex.Scheduler

class TestSchedulers : Schedulers {

    override fun mainThread(): Scheduler {
        return io.reactivex.schedulers.Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return io.reactivex.schedulers.Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return io.reactivex.schedulers.Schedulers.trampoline()
    }
}