package com.jmoney.discover.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmoney.discover.R
import com.jmoney.discover.di.AppComponentProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as AppComponentProvider)
            .provideAppComponent()
            .inject(this)
        setContentView(R.layout.activity_main)
    }
}
