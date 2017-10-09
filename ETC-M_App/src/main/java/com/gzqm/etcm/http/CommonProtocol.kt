package com.xiaodan.racinggame.http

import com.common.http.HttpResult
import com.gzqm.etcm.common.Constant
import com.gzqm.etcm.http.RetrofitManager
import com.gzqm.etcm.http.upload.UploadListener
import com.xiaodan.racinggame.event.EM_UploadProgress
import org.greenrobot.eventbus.EventBus
import rx.Observable
import java.io.File
import java.util.HashMap

/** 公用的借口参数类 */
class CommonProtocol{
    companion object {
        @JvmStatic
        fun uploadFiles(): Observable<HttpResult<Any>>{
            val params = HashMap<String, Any>()
            params.put("store_id", 35)
            params.put("user_id", 3)
            params.put("task_id", 98)
            params.put("image_type", 1)
            val files = listOf(File(Constant.SDPATH+"Download/a.jpg"), File(Constant.SDPATH+"Download/b.jpg"))
            return RetrofitManager.getInstance()
                    .getCommonService()
                    .uploadFileWithRequestBody(
                            filesToMultipartBody(createParams(params, "addPhoto"), paramsName = "image_file",files = files)
                    )
        }
        @JvmStatic
        fun uploadFiles_2(): Observable<HttpResult<Any>>{
            var listener = object : UploadListener {
                override fun onRequestProgress(bytesWritten: Long, contentLength: Long) {
                    EventBus.getDefault().post(EM_UploadProgress(bytesWritten, contentLength))
                }
            }
            val params = HashMap<String, Any>()
            params.put("store_id", 35)
            params.put("user_id", 3)
            params.put("task_id", 98)
            params.put("image_type", 1)
            val files = listOf(File(Constant.SDPATH+"Download/a.jpg"), File(Constant.SDPATH+"Download/b.jpg"))
            return RetrofitManager.getInstance()
                    .getUploadService(listener)
                    .uploadFileWithRequestBody(
                            filesToMultipartBody(createParams(params, "addPhoto"), paramsName = "image_file",files = files)
                    )
        }
    }
}
