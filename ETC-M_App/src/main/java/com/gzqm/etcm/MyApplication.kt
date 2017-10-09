package com.gzqm.etcm

import android.support.multidex.MultiDexApplication

/**
 * Created by LiesLee on 17/8/14.
 */

class MyApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }


    companion object{
        @Volatile lateinit var INSTANCE: MyApplication
    }
}