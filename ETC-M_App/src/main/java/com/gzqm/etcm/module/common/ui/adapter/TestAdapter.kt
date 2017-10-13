package com.gzqm.etcm.module.common.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.common.base.presenter.BasePresenter
import com.common.base.ui.BaseActivity
import com.common.base.ui.BaseAdapter
import com.gzqm.etcm.R

/**
 * Created by LiesLee on 2017/10/10.
 * Email: LiesLee@foxmail.com
 */
class TestAdapter(data: List<String>?) : BaseAdapter<String, BaseViewHolder>(R.layout.item_test, data){
    override fun convert(holder: BaseViewHolder?, data: String?) {
        holder?.apply {
            setText(R.id.tv_test, "${getFinalPositionOnList(holder)}")
            holder.addOnClickListener(R.id.iv_img)
        }
    }

}