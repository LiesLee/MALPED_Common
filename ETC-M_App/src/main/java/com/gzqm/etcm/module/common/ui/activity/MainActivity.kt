package com.gzqm.etcm.module.common.ui.activity

import android.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.common.ShiHuiActivityManager
import com.common.annotation.ActivityFragmentInject
import com.common.base.presenter.BasePresenterImpl
import com.common.base.ui.BaseActivity
import com.common.base.ui.BaseTakePhotoActivity
import com.common.dialog.CommonDialog
import com.gzqm.etcm.R
import com.gzqm.etcm.module.home.ui.fragment.HomeFragment
import com.gzqm.etcm.module.itinerary.ui.fragment.ItineraryFragment
import com.gzqm.etcm.module.my.ui.fragment.MyFragment
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
class MainActivity : BaseActivity<BasePresenterImpl<*>>() {


    var homeFragment: HomeFragment? = null
    var itineraryFragment: ItineraryFragment? = null
    var myfragment: MyFragment? = null


    override fun initData() {

    }

    override fun initView() {
        homeFragment = HomeFragment()

        val hide = { fragment: Fragment? ->
            fragment?.apply {
                supportFragmentManager.beginTransaction().hide(this).commitAllowingStateLoss()
            }

        }
        val show = { fragment: Fragment? ->
            fragment?.apply {
                supportFragmentManager.beginTransaction().show(this).commitAllowingStateLoss()
            }

        }
        val add = { fragment: Fragment? ->
            fragment?.apply {
                supportFragmentManager.beginTransaction().add(R.id.fl_fragment, this).commitAllowingStateLoss()
            }

        }

        add(homeFragment)

        rg_main.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.rb_home -> {
                    //先隐藏
                    hide(itineraryFragment)
                    hide(myfragment)
                    if (homeFragment == null) {
                        homeFragment = HomeFragment()
                        add(homeFragment)
                    } else show(homeFragment)


                }
                R.id.rb_find -> {
                    if (itineraryFragment == null) {
                        itineraryFragment = ItineraryFragment()
                        add(itineraryFragment)
                    } else show(itineraryFragment)
                }

                R.id.rb_person -> {
                    if(myfragment == null){
                        myfragment = MyFragment()
                        add(myfragment)
                    }else show(myfragment)
                }
            }
        }

        tv_title.setOnClickListener(this)
    }

    override fun onViewClick(view: View?) {
        when(view!!.id){
            R.id.tv_title -> CommonDialog.show2btnDialog(baseActivity, "提示", "取消", "确认",false,{},{ toast("确认")})
        }

    }


    private var exitTime = 0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                toast("再按一次退出应用")
                exitTime = System.currentTimeMillis()
            } else {
                ShiHuiActivityManager.getInstance().cleanActivity()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
