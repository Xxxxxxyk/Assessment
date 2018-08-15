package com.dream.assessment.impl;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dream.assessment.adapter.HomeAdapter;
import com.dream.assessment.adapter.ProjectAdapter;
import com.dream.assessment.assessment.R;
import com.dream.assessment.base.BaseFragment;
import com.dream.assessment.bean.ProjectGroup;
import com.dream.assessment.bean.ProjectGroupDao;
import com.dream.assessment.bean.ProjectGroupImpl;
import com.dream.assessment.utils.GreenDaoHelper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {
    private RecyclerView mRv_list;
    private List<ProjectGroupImpl> mList;
    private HomeAdapter mHomeAdapter;


    @Override
    public int getViewID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mRv_list = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    public void initListerenAndAdapter() {
        mList = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(getContext(), mList);
        mRv_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRv_list.setAdapter(mHomeAdapter);

    }

    @Override
    public void initData() {
        mList.clear();
        ProjectGroupDao projectGroupDao = GreenDaoHelper.getDaoSession().getProjectGroupDao();
        List<ProjectGroup> projectGroups = projectGroupDao.loadAll();
        for (int i = 0; i < projectGroups.size(); i++) {
            mList.add(new ProjectGroupImpl(projectGroups.get(i).get_id(),projectGroups.get(i).getName(),projectGroups.get(i).getProgect_group_id(),projectGroups.get(i).getAdmin(),false));
        }

        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void otherClick(@Nullable View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}