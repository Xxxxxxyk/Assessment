package com.dream.assessment.assessment

import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.dream.assessment.activitys.JoinActivity
import com.dream.assessment.base.BaseActivity
import com.dream.assessment.bean.TabEntity
import com.dream.assessment.impl.HomeFragment
import com.dream.assessment.impl.MeFragment
import com.dream.assessment.impl.MesFragment
import com.dream.assessment.impl.ServiceFragment
import com.dream.assessment.utils.Config
import com.dream.assessment.utils.ViewFindUtils.find
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    private var mTabLayout: CommonTabLayout? = null
    private var mTabEntities = ArrayList<CustomTabEntity>()
    private var mFragment = ArrayList<Fragment>()
    private var mFragment_home_view = HomeFragment()
    private var mFragment_service_view = ServiceFragment()
    private var mFragment_mes_view = MesFragment()
    private var mFragment_me_view = MeFragment()

    override fun getViewID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
    }

    override fun initData() {

        mFragment.add(mFragment_home_view)
        mFragment.add(mFragment_service_view)
        mFragment.add(mFragment_mes_view)
        mFragment.add(mFragment_me_view)

        var i = 0
        for (title in Config.mTitles) {
            mTabEntities.add(TabEntity(title, Config.mIconSelectIds[i], Config.mIconUnselectIds[i]))
            i++
        }

        mTabLayout = find(window.decorView, R.id.tl_2)
        mTabLayout!!.setTabData(mTabEntities, this, R.id.fr_view, mFragment)
        mTabLayout!!.showDot(2)
    }

    override fun initListerenAndAdapter() {
        tv_join.setOnClickListener(this)

        mTabLayout!!.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {

                tv_title.text = Config.mTitles.get(position)

                if (position == 2) {
                    toolbar.visibility = View.GONE
                } else {
                    toolbar.visibility = View.VISIBLE
                }

                hideJoin(position)
            }


            override fun onTabReselect(position: Int) {
            }
        })

    }


    //设置加入是否显示
    private fun hideJoin(position: Int) {
        when (position) {
            0 -> tv_join.visibility = View.VISIBLE
            else -> tv_join.visibility = View.GONE
        }
    }

    override fun otherClick(v: View?) {
        when (v!!.id) {
            R.id.tv_join -> startActivity(Intent(this@MainActivity, JoinActivity::class.java))
        }
    }


}
