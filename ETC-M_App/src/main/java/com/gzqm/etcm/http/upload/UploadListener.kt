package com.gzqm.etcm.http.upload

/**
 * Created by LiesLee on 2017/9/29.
 * Email: LiesLee@foxmail.com
 */
interface UploadListener{
    fun onRequestProgress(bytesWritten: Long, contentLength: Long)
}