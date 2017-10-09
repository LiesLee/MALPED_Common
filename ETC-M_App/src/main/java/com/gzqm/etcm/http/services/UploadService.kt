package com.xiaodan.racinggame.http.services

import com.common.http.HttpResult
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import rx.Observable

/**
 * Created by LiesLee on 2017/9/28.
 * Email: LiesLee@foxmail.com
 */
interface UploadService{
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

