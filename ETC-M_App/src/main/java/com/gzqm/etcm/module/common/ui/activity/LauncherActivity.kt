package com.gzqm.etcm.module.common.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.common.annotation.ActivityFragmentInject
import com.common.base.presenter.BasePresenterImpl
import com.common.base.ui.BaseActivity
import com.common.dialog.CommonDialog
import com.gzqm.etcm.BuildConfig
import com.gzqm.etcm.MyApplication
import com.gzqm.etcm.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.socks.library.KLog
import com.xiaodan.racinggame.http.getUniquePsuedoID
import java.util.*

/**
 * Created by LiesLee on 17/8/14.
 */
@ActivityFragmentInject(contentViewId = R.layout.act_launcher)
class LauncherActivity : BaseActivity<BasePresenterImpl<*>>(),MultiplePermissionsListener{
    override fun onViewClick(view: View?) {
    }

    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken) {
        token.continuePermissionRequest()
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
        if(report.deniedPermissionResponses == null || report.deniedPermissionResponses.isEmpty()){
            iniTimer()
        } else {
            CommonDialog.show2btnDialog(baseActivity, "缺少部分权限将无法正常使用，请打开您系统应用设置页面点击\"${resources.getString(R.string.app_name)}\"，然后开启相关权限！", "取消", "好的",
                    false, { finish() }, {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", baseActivity.packageName, null)
                intent.data = uri
                startActivity(intent)
                finish()
            }

            ).setCancelable(false)
        }
    }

    //val pList = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)


    override fun initView() {
        Dexter.initialize(MyApplication.INSTANCE) //权限封装类
        //  获取权限
        //Dexter.checkPermissions(this@LauncherActivity, pList)
        iniTimer()
        //初始化SDK
        MyApplication.INSTANCE.initSdks()

        KLog.e("", getUniquePsuedoID())
    }


    override fun initData() {

    }




    var time = 0L
    lateinit var timer: Timer
    var c_time = 0L
    //倒计时，控制页面跳转
    private fun iniTimer() {
        time = System.currentTimeMillis()
        timer = Timer()// 实例化Timer类
        timer.schedule(object : TimerTask() {
            override fun run() {
                c_time = System.currentTimeMillis()
                if (c_time - time > 1500) { //大于*秒直接跳过
                    gotoMainActivity()
                }
            }
        }, 0, 500)// 这里百毫秒
    }

    fun gotoMainActivity(){
        timer.cancel()
        val faceIntent = Intent(baseActivity, MainActivity::class.java)
        startActivity(faceIntent)
        //finish()
    }
}
