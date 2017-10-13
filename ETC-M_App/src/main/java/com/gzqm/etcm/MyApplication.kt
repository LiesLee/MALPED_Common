package com.gzqm.etcm

import android.support.multidex.MultiDexApplication
import com.socks.library.KLog

/**
 * Created by LiesLee on 17/8/14.
 */

class MyApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initSdks()
    }


    companion object{
        @Volatile lateinit var INSTANCE: MyApplication
    }

    public fun initSdks(){
        KLog.init(BuildConfig.DEBUG)
    }
}