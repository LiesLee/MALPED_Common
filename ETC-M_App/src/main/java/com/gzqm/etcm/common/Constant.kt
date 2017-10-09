package com.gzqm.etcm.common

import android.os.Environment

/**
 * Created by LiesLee on 2017/9/26.
 * Email: LiesLee@foxmail.com
 */
class Constant{
    companion object {
        @JvmField
        var SDPATH = Environment.getExternalStorageDirectory().toString() + "/"
        @JvmField
        val APP_DEVICEID_UUID = "App_Deviceid_UUID"
        @JvmField
        val USER = "user"
        @JvmField
        val TOKEN = "token"
    }
}
