package com.dream.assessment.impl

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.dream.assessment.assessment.R
import com.dream.assessment.base.BaseFragment

class ServiceFragment : BaseFragment() {
    override fun initView() {
    }

    override fun getViewID(): Int {
        return R.layout.fragment_service
    }

    override fun initListerenAndAdapter() {
    }

    override fun initData() {
    }

    override fun otherClick(v: View?) {
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("我在加载数据")
    }

}