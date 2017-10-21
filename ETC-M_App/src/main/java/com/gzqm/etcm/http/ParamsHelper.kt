package com.xiaodan.racinggame.http

import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import com.common.utils.MD5Util
import com.common.utils.UUID
import com.gzqm.etcm.BuildConfig
import com.gzqm.etcm.MyApplication
import com.gzqm.etcm.R
import com.gzqm.etcm.utils.SpUtil
import com.socks.library.KLog
import okhttp3.MediaType
import java.util.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.collections.ArrayList

private fun getSignKey(): String =
        if (BuildConfig.DEBUG) MyApplication.INSTANCE.resources.getString(R.string.sign_key)
        else MyApplication.INSTANCE.resources.getString(R.string.sign_key_release)

/**
 * Created by LiesLee on 17/8/17.
 */
fun createParams(parameters: Map<String, Any>, urlName: String): SortedMap<String, Any> {

    val hashMap = HashMap(parameters)
    try {
        hashMap.put("app_version", MyApplication.INSTANCE.getPackageManager().getPackageInfo(MyApplication.INSTANCE.getPackageName(), PackageManager.GET_META_DATA).versionName)
        hashMap.put("client_time", System.currentTimeMillis())
        hashMap.put("system_version", Build.VERSION.RELEASE)
        hashMap.put("device_id", getDeviceId())
        hashMap.put("utm_medium", "android")
        hashMap.put("utm_source", "teeApp")


        if (!hashMap.containsKey("api_ver")) {
            hashMap.put("api_ver", MyApplication.INSTANCE.resources.getString(R.string.api_ver))
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }


    val map = TreeMap(hashMap)
    map.put("api_sign", createSign(map))
    map.put("a", urlName)
    return map
}


fun getParams(parameters: SortedMap<String, Any>): String {
    val sb = StringBuffer()
    val es = parameters.entries//所有参与传参的参数按照accsii排序（升序）
    for ((key, value) in es) {
        if (value != null) {
            sb.append("$key=$value&")
        }
    }
    return sb.toString()
}


fun createSign(parameters: SortedMap<String, Any>): String {
    val sb = StringBuffer()
    sb.append(getParams(parameters))
    sb.append(getSignKey())
    return MD5Util.MD5Encode(sb.toString(), "utf-8")
}


/** 生成的设备号  */
val APP_DEVICEID_UUID = "App_Deviceid_UUID"
//fun getDeviceId(): String? {
//    var szImei: String? = null
//    val telephonyMgr = MyApplication.INSTANCE.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//    if (ActivityCompat.checkSelfPermission(MyApplication.INSTANCE, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//        szImei = SpUtil.readString(APP_DEVICEID_UUID)
//        if (StringUtils.isEmpty(szImei)) {
//            szImei = UUID.randomUUID().toString()
//            SpUtil.writeString(APP_DEVICEID_UUID, szImei)
//        }
//    } else {
//        szImei = telephonyMgr.deviceId
//    }
//
//    if (StringUtils.isEmpty(szImei)) {
//        szImei = SpUtil.readString(APP_DEVICEID_UUID)
//        if (StringUtils.isEmpty(szImei)) {
//            szImei = UUID.randomUUID().toString()
//            SpUtil.writeString(APP_DEVICEID_UUID, szImei)
//        }
//    }
//    return szImei
//}

fun getDeviceId(): String? {
    var szImei: String? = null

    szImei = SpUtil.readString(APP_DEVICEID_UUID)

    if(!TextUtils.isEmpty(szImei)) return szImei

    if (TextUtils.isEmpty(szImei))  szImei = getUniquePsuedoID()

    if (TextUtils.isEmpty(szImei)) szImei = UUID.randomUUID().toString()

    SpUtil.writeString(APP_DEVICEID_UUID, szImei)
    return szImei
}


//获得独一无二的Psuedo ID
fun getUniquePsuedoID(): String {
    var serial: String? = null
    val m_szDevIDShort = "35" +
            Build.BOARD.length % 10 + Build.BRAND.length % 10 +

            Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 +

            Build.DISPLAY.length % 10 + Build.HOST.length % 10 +

            Build.ID.length % 10 + Build.MANUFACTURER.length % 10 +

            Build.MODEL.length % 10 + Build.PRODUCT.length % 10 +

            Build.TAGS.length % 10 + Build.TYPE.length % 10 +

            Build.USER.length % 10 //13 位
    try {
        serial = android.os.Build::class.java.getField("SERIAL").get(null).toString()
        //API>=9 使用serial号
        return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
    } catch (exception: Exception) {
        //serial需要一个初始化
        serial = UUID.randomUUID().toString() // 随便一个初始化
        KLog.e("","设备id  serial获取失败")
    }

    //使用硬件信息拼凑出来的15位号码
    return UUID(m_szDevIDShort.hashCode().toLong(), serial!!.hashCode().toLong()).toString()
}


fun filesToMultipartBody(params: Map<String, Any>, paramsName: String, type: MediaType = MediaType.parse("image/jpg"), files: List<File>): MultipartBody {
    val builder = MultipartBody.Builder()

    for (file in files) {
        val requestBody = RequestBody.create(type, file)
        builder.addPart(MultipartBody.Part.createFormData(paramsName, file.name, requestBody))
    }

    for ((key, value) in params) {
        builder.addPart(MultipartBody.Part.createFormData(key, value?.toString()))
    }


//    for (file in files) {
//        // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
//        val requestBody = RequestBody.create(type, file)
//        builder.addFormDataPart(paramsName, file.name, requestBody)
//    }
    builder.setType(MultipartBody.FORM)
    return builder.build()
}

fun filesToMultipartBodyParts(params: Map<String, Any>, paramsName: String, type: MediaType = MediaType.parse("image/jpg"), files: List<File>): List<MultipartBody.Part> {
    val parts = ArrayList<MultipartBody.Part>()
    parts.addAll(files.map {
        val requestBody = RequestBody.create(type, it)
        MultipartBody.Part.createFormData(paramsName, it.name, requestBody)
    })


    parts.addAll(params.map {
        MultipartBody.Part.createFormData(it.key, if (it.value == null) null else it.value.toString())
    })
    return parts
}