package com.dream.assessment.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dream.assessment.assessment.R
import com.blankj.utilcode.util.FragmentUtils
import com.dream.assessment.interfaces.IBaseView

abstract class BaseFragment : Fragment(), IBaseView, View.OnClickListener {

    private lateinit var root_view : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root_view = View.inflate(getContext(), getViewID(), null);
        initView()
        initListerenAndAdapter()
        initData()
        return root_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun findViewById(id: Int): View {
        return root_view!!.findViewById<View>(id)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.back -> {
                FragmentUtils.remove(this)
            }
            else -> {
                otherClick(view)
            }
        }
    }
}