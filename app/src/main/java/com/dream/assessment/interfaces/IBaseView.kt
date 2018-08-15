package com.dream.assessment.interfaces

import android.view.View

interface IBaseView {

    /**
     * 书写布局
     */
    fun getViewID() : Int

    fun initView()

    /**
     * 监听器和适配器
     */
    fun initListerenAndAdapter()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 点击事件
     */
    fun otherClick(v : View?)

}