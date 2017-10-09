package com.gzqm.etcm.http

import android.support.annotation.IntDef
import com.gzqm.etcm.BuildConfig

/**
 * Created by LiesLee on 2017/9/29.
 * Email: LiesLee@foxmail.com
 */

fun getHosts(hostsType: Int = HostType.DEFAULT_HOST) : String = when(hostsType){
    HostType.DEFAULT_HOST -> getDefaultHosts()
    else -> ""
}

private fun getDefaultHosts(): String =
        when (if (BuildConfig.DEBUG) BuildConfig.HOST_IS_DEBUG else BuildConfig.DEBUG) {
            true -> if (BuildConfig.HOST_GRAY) "www.baidu.com/gray/" else "www.baidu.com/test/" //灰度和测试
            else -> "www.baidu.com/"    //正式服
        }


object HostType {

    /**
     * 多少种Host类型
     */
    val TYPE_COUNT = 2

    /**
     * 用户host
     */
    @HostTypeChecker
    const val DEFAULT_HOST = 1

    /**
     * 商户host
     */
    @HostTypeChecker
    const val OTHER_HOST = 2


    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef(DEFAULT_HOST.toLong(), OTHER_HOST.toLong())
    @Retention(AnnotationRetention.SOURCE)
    annotation class HostTypeChecker

}