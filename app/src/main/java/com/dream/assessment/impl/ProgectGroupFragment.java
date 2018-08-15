package com.dream.assessment.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.dream.assessment.adapter.ProjectGroupMsgAdapter;
import com.dream.assessment.base.BaseFragment;
import com.dream.assessment.assessment.R;

import org.jetbrains.annotations.Nullable;

public class ProgectGroupFragment extends BaseFragment {

    private RecyclerView mRv_pro_group_msg;
    private ProjectGroupMsgAdapter mProjectGroupMsgAdapter;

    @Override
    public int getViewID() {
        return R.layout.fragment_progect_group;
    }

    @Override
    public void initView() {
        mRv_pro_group_msg = (RecyclerView) findViewById(R.id.rv_pro_group_msg);
    }

    @Override
    public void initListerenAndAdapter() {
        mProjectGroupMsgAdapter = new ProjectGroupMsgAdapter(getContext());
        mRv_pro_group_msg.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRv_pro_group_msg.setAdapter(mProjectGroupMsgAdapter);

    }

    @Override
    public void initData() {
        if (mProjectGroupMsgAdapter != null) {
            mProjectGroupMsgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void otherClick(@Nullable View v) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            LogUtils.e("加载数据");
            initData();
        }
    }
}
