package com.gzqm.etcm.module.common.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.common.annotation.ActivityFragmentInject
import com.common.base.presenter.BasePresenterImpl
import com.common.base.ui.BaseActivity
import com.common.base.ui.BaseTakePhotoActivity
import com.gzqm.etcm.R
import com.jph.takephoto.model.TImage
import com.jph.takephoto.model.TResult
import com.socks.library.KLog
import kotlinx.android.synthetic.main.act_main.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by LiesLee on 2017/9/25.
 * Email: LiesLee@foxmail.com
 */
@ActivityFragmentInject(contentViewId = R.layout.act_main, toolbarTitle = R.string.app_name)
class MainActivity : BaseTakePhotoActivity<BasePresenterImpl<*>>(){

    val list = ArrayList<TImage>(0)

    override fun currentCount(): Int {
        return list.size
    }

    override fun takeCancel() {

    }

    override fun takeSuccess(result: TResult?) {
        result?.apply {
            list.addAll(this.images)
        }
    }

    override fun getMaxCount(): Int {
        return 9
    }

    override fun takeFail(result: TResult?, msg: String?) {
        KLog.e(msg)
    }

    override fun initData() {

    }

    override fun initView() {
        tv_title.setOnClickListener{
            showSelectePhotoItems()
        }

        tv_compress.setOnClickListener{
            showProgress(0)
            compressImages(1024*500, list){ isSuccess: Boolean, results: Collection<String>?, msg: String ->
                val jo = JSONObject()
                val ja = JSONArray()

                if (results != null) {
                    for (s in results){
                        ja.put(s)
                    }
                }
                jo.put("isCompressSuccess", isSuccess)
                jo.put("results", ja)
                jo.put("msg", msg)

                KLog.json(jo.toString())

                hideProgress(0)
            }
        }
     }

    override fun onClick(v: View?) {

    }
}
