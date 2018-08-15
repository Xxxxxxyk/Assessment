package com.dream.assessment.impl;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.dream.assessment.adapter.MyFragmentPagerAdapter;
import com.dream.assessment.assessment.R;
import com.dream.assessment.base.BaseFragment;
import com.dream.assessment.utils.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MesFragment extends BaseFragment {

    private ViewPager mVp;
    private MyFragmentPagerAdapter mAdapter;
    private List<Fragment> list = new ArrayList<>();
    String[] mMesTitles = {"项目组", "项目"};

    @Override
    public int getViewID() {
        return R.layout.fragment_mes;
    }

    @Override
    public void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        list.add(new ProgectGroupFragment());
        list.add(new ProgectFragment());
    }

    @Override
    public void initListerenAndAdapter() {
        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), list);
        mVp.setAdapter(mAdapter);
        final SegmentTabLayout tabLayout = (SegmentTabLayout) findViewById(R.id.tl_3);
        tabLayout.setTabData(mMesTitles);
        tabLayout.setCurrentTab(0);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mVp.setCurrentItem(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void otherClick(@Nullable View v) {

    }


    @Override
    public void onResume() {
        super.onResume();
        initListerenAndAdapter();
    }
}