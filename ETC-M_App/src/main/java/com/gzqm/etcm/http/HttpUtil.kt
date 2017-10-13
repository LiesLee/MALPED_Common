package com.xiaodan.racinggame.http

import com.common.base.ui.BaseView
import com.common.callback.RequestCallback
import com.common.http.HttpResult
import com.common.http.HttpSubscibe
import com.common.utils.NetUtil
import com.gzqm.etcm.BuildConfig
import com.gzqm.etcm.MyApplication
import com.gzqm.etcm.R
import com.socks.library.KLog
import retrofit2.adapter.rxjava.HttpException
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.net.SocketTimeoutException
import java.util.*

/**
 * Created by LiesLee on 17/8/17.
 */

//val hosts = "http://39.108.62.62/interface.php/"
val hosts = "http://139.198.5.221/test/"
//val hosts = "http://test.jsb-app.com/"


//fun getSignKey(): String {
//    return if (BuildConfig.DEBUG)
//        MyApplication.INSTANCE.getResources().getString(R.string.sign_key)
//    else
//        MyApplication.INSTANCE.resources.getString(R.string.sign_key_release)
//}


private interface DoSpecialErrorCode<T> {
    fun callback(t: HttpResult<T>)
}

fun <T> httpRequestDefault(observable: Observable<HttpResult<T>>, baseView: BaseView?, requestCallback: RequestCallback<T>?): Subscription? {
    if (NetUtil.isConnected(MyApplication.INSTANCE)) {
        return HttpSubscibe.subscibe(observable, { baseView?.apply { requestCallback.apply { this?.beforeRequest() } } },
                AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread(), false, baseView, object : Subscriber<HttpResult<T>>() {
            var checkBack = fun(back: (RequestCallback<T>) -> Unit) {
                baseView?.apply {
                    requestCallback?.apply {
                        back(this)
                    }
                }
            }

            override fun onError(e: Throwable?) {
                checkBack {
                    when (e) {
                        is HttpException -> {             //HTTP错误
                            KLog.e(e.getLocalizedMessage() + "\n" + e)
                            it.requestError(e.code(), e.getLocalizedMessage() + "\n" + e)
                        }
                        is SocketTimeoutException -> {
                            KLog.e("请求超时：\n" + e)
                            it.requestError(1, "似乎网络不太给力哦~")
                        }
                        else -> {
                            e?.apply { KLog.e(getLocalizedMessage() + "\n" + e) }
                            it.requestError(2, "似乎链接不上哦，请稍后再试~")
                        }
                    }
                }
            }

            override fun onCompleted() {
                checkBack { it.requestComplete() }
            }

            override fun onNext(t: HttpResult<T>?) {
                checkBack {
                    doOnSuccess(t, baseView, requestCallback, object : DoSpecialErrorCode<T>{
                        override fun callback(t: HttpResult<T>) {
                            when(t.status){
                                200 -> it.requestSuccess(t.data)
                                201 -> it.requestError(3, "登录状态失效，请从新登录") //可做成刷新token 再调
                            }
                        }
                    })
                }
            }

        })

    } else {
        baseView?.apply {
            requestCallback.apply {
                this?.requestError(404, "检查不到网络哦, 请检查网络状态")
            }
        }
    }

    return null

}


private fun <T> doOnSuccess(t: HttpResult<T>?, baseView: BaseView?, requestCallback: RequestCallback<T>?, specialError: DoSpecialErrorCode<T>?): HttpResult<T> {
    baseView?.apply {
        requestCallback?.apply {
            try {
                if (t != null) {
                    when (t.status) {
                        200 ->
                            if (specialError != null) {
                                specialError.callback(t)
                            } else {
                                requestError(3, "数据回调后处理错误")
                            }

                        202, 207 -> requestError(202, "由于您长时间未使用" + MyApplication.INSTANCE.getResources().getString(R.string.app_name) + ", 登录状态失效, 请重新登录!")


                        201 -> //token状态失效，刷新token
                            if (specialError != null) {
                                specialError.callback(t)
                            } else {
                                requestError(3, "数据回调后处理错误")
                            }


                        else -> requestError(t.getStatus(), t.getMsg())

                    }

                } else {
                    requestError(3, "数据获取失败");
                }


            } catch (e: Exception) {
                KLog.e("requestCallback", "数据回调后处理错误: $e")
                e.printStackTrace()
                requestError(3, "数据回调后处理错误")
            }
        }
    }

    return t!!

}

//private fun <T> refreshTokenAndThenRequestAgain(observable: Observable<HttpResult<T>>, subscribeOnScheduler: Scheduler,
//                                                observeOnScheduler: Scheduler, cancelUnsubscribes: CompositeSubscription, baseView: BaseView,
//                                                requestCallback: RequestCallback<T>): Subscription? {
//
//
//
//    return null
//}



