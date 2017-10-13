package com.gzqm.etcm.module.home.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.common.annotation.ActivityFragmentInject
import com.common.base.presenter.BasePresenterImpl
import com.common.base.ui.BaseFragment
import com.gzqm.etcm.R
import com.gzqm.etcm.module.common.ui.adapter.TestAdapter
import com.gzqm.etcm.utils.GlideUtil
import com.views.ViewsHelper
import com.views.util.ToastUtil

import kotlinx.android.synthetic.main.fra_home.*
import me.dkzwm.widget.srl.RefreshingListenerAdapter
import org.jetbrains.anko.find

/**
 * Created by LiesLee on 2017/10/10.
 * Email: LiesLee@foxmail.com
 */
@ActivityFragmentInject(contentViewId = R.layout.fra_home)
class HomeFragment : BaseFragment<BasePresenterImpl<*>>() {


    val list:ArrayList<String> = ArrayList(listOf( "", "", "", "", "", "", "", "", "", ""))
    var mAdapter: TestAdapter?  = null
    override fun initView(fragmentRootView: View?) {
        mAdapter = TestAdapter(null)
        val header = LayoutInflater.from(context).inflate(R.layout.header_home, rv_list.parent as ViewGroup, false)

        val bgaBanner = header.findViewById(R.id.banner_main_fade) as BGABanner
        val bannerAdapter = BGABanner.Adapter<ImageView, String> { banner, itemView, model, position ->
            GlideUtil.loadImage(this@HomeFragment, model, itemView)
        }
        bgaBanner.setData(listOf("https://www.leica-camera.cn/sites/default/files/image/window/2016-05/Teaser_window_wetzlar-1200x470.jpg",
                "https://www.leica-camera.cn/sites/default/files/image/window/2016-05/Paolo-Solari-Bozzi_teaser-1200x470.jpg",
                "https://www.leica-camera.cn/sites/default/files/image/window/2016-05/Window-Teaser_Elliott-Erwitt_teaser-1200x470.jpg"), null)

        bgaBanner.setAdapter(bannerAdapter)
        bgaBanner.setDelegate { banner, itemView, model, position ->
            ToastUtil.showShortToast(baseActivity, "点击：$position")
        }


        mAdapter!!.addHeaderView(header)
        rv_list.layoutManager = LinearLayoutManager(baseActivity)
        rv_list.adapter = mAdapter


        ViewsHelper.initSmoothRefreshLayoutByMaterial(msl_refresh, object : RefreshingListenerAdapter(){
            override fun onRefreshBegin(isRefresh: Boolean) {
                if (isRefresh){
                    msl_refresh.postDelayed({
                        mAdapter!!.data = list
                        msl_refresh.refreshComplete()
                    }, 3000)
                }
            }
        }).autoRefresh()


        mAdapter!!.setOnLoadMoreListener({ addData() }, rv_list)
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            toast("item：$position")
        }

        mAdapter!!.setOnItemChildClickListener { adapter, view, position ->
            toast("view：${view.javaClass.name}")
        }


    }

    private fun addData() {
        rv_list.postDelayed({ mAdapter!!.addNewData(ArrayList(listOf( "", "", "", "", "", "", "", "", "", "")), 30) },2000)
    }

    override fun initData() {

    }
    override fun onViewClick(view: View?) {

    }

}