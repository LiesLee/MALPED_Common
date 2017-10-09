package com.gzqm.etcm.http

import android.util.SparseArray
import com.alibaba.fastjson.JSON
import com.common.utils.logMessageQueue.ThreadPoolManager
import com.gzqm.etcm.BuildConfig
import com.gzqm.etcm.http.upload.UpLoadProgressInterceptor
import com.gzqm.etcm.http.upload.UploadListener
import com.socks.library.KLog
import com.xiaodan.racinggame.http.hosts
import com.xiaodan.racinggame.http.services.CommonService
import com.xiaodan.racinggame.http.services.UploadService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import java.lang.Long
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * Created by LiesLee on 2017/9/29.
 * Email: LiesLee@foxmail.com
 */
class RetrofitManager{

    companion object {
        // 管理不同HostType的单例
        val sInstanceManager = SparseArray<RetrofitManager>(HostType.TYPE_COUNT)
        /**
         * 获取单例
         *
         * @param hostType host类型
         * @return 实例
         */
        @JvmStatic
        fun getInstance(hostType: Int = HostType.DEFAULT_HOST): RetrofitManager {
            var instance: RetrofitManager? = sInstanceManager.get(hostType)
            return if (instance == null) {
                instance = RetrofitManager(hostType)
                sInstanceManager.put(hostType, instance)
                instance
            } else {
                instance
            }
        }

    }

    var sOkHttpClient : OkHttpClient? = null
    var isMsgQueueLog = false
    var tpm : ThreadPoolManager? = null

    private var hostType = 1



    private constructor()

    private constructor(@HostType.HostTypeChecker hostType: Int){
        this.hostType = hostType
    }




    /** Response拦截器  */
    private val mLoggingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body()
        val contentLength = responseBody.contentLength()

        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer()

        var charset = Charset.forName("UTF-8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(charset)
            } catch (e: UnsupportedCharsetException) {
                KLog.e("Couldn't decode the response body; charset is likely malformed.")
                return@Interceptor response
            }

        }
        if (BuildConfig.DEBUG) {
            val jo = JSON.parseObject(buffer.clone().readString(charset))
            if (contentLength != 0L) {
                if (isMsgQueueLog) {
                    if (tpm == null) {
                        tpm = ThreadPoolManager.newInstance()
                    }
                    tpm!!.addLogMsg(jo.toJSONString())
                } else {
                    jo?.apply {
                        val sj = this.toString()
                        KLog.json(sj)
                        KLog.e("Data:" , sj.trim())
                    }

                }
            }
        }
        response
    }


    // 配置OkHttpClient
    private fun getOkHttpClient(): OkHttpClient {
        if (sOkHttpClient == null) {
            synchronized(RetrofitManager::class.java) {
                if (sOkHttpClient == null) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    sOkHttpClient = OkHttpClient.Builder()
                            .addInterceptor(mLoggingInterceptor)    //拦截请求结果打印
                            .addInterceptor({ chain ->
                                val original = chain.request()
                                val builder = original.newBuilder()
                                builder.addHeader("Content-Type", "application/json")
                                builder.addHeader("charset", "UTF-8")
                                val request = builder.build()
                                chain.proceed(request)
                            }) //设置请求头
                            .addInterceptor(loggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .readTimeout(120, TimeUnit.SECONDS)
                            .writeTimeout(180, TimeUnit.SECONDS)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .build()
                }
            }
        }
        return sOkHttpClient!!
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(hosts)
                .client(getOkHttpClient())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

    private var commonService: CommonService? = null
    private var uploadService: UploadService? = null

    fun getCommonService(): CommonService {
        if (commonService == null) {
            commonService = createRetrofit().create<CommonService>(CommonService::class.java)
        }
        return commonService!!
    }

    fun getUploadService(listener: UploadListener): UploadService{
        val interceptor = UpLoadProgressInterceptor(listener)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //StreamAllocation的数量会渐渐变成0，从而被线程池监测到并回收，自动释放机制
        val uploadOkhttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor{ chain ->
                    val original = chain.request()
                    val builder = original.newBuilder()
                    builder.addHeader("Content-Type", "application/json")
                    builder.addHeader("charset", "UTF-8")
                    val request = builder.build()
                    chain.proceed(request)}
                .addInterceptor(mLoggingInterceptor )
                .retryOnConnectionFailure(true)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
        return Retrofit.Builder().baseUrl(hosts).client(uploadOkhttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(UploadService::class.java)
    }



}