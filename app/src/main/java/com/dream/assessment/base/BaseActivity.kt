package com.dream.assessment.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dream.assessment.assessment.R
import com.dream.assessment.interfaces.IBaseView

abstract class BaseActivity : AppCompatActivity() , View.OnClickListener ,IBaseView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewID())

        initView()

        initData()

        initListerenAndAdapter()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.back -> { finish() }
            else -> { otherClick(view)}
        }
    }
}