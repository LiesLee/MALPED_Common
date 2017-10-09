package com.xiaodan.racinggame.http.services

import com.common.http.HttpResult
import rx.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * Created by LiesLee on 17/8/17.
 */
interface CommonService{
    /**
     * 
     * @param params
     * *
     * @return
     */
    @FormUrlEncoded
    @POST("commercial")
    fun commonString(@FieldMap params: Map<String, @JvmSuppressWildcards Any>): Observable<HttpResult<String>>

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
    </MultipartBody.Part> */
    @Multipart
    @POST("api.php")
    fun uploadFilesWithParts(@Part parts: List<MultipartBody.Part>): Observable<HttpResult<Any>>

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("api.php")
    fun uploadFileWithRequestBody(@Body multipartBody: MultipartBody): Observable<HttpResult<Any>>
}