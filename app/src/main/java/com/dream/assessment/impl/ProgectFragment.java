package com.dream.assessment.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dream.assessment.adapter.ProjectMsgAdapter;
import com.dream.assessment.base.BaseFragment;
import com.dream.assessment.assessment.R;

import org.jetbrains.annotations.Nullable;

public class ProgectFragment extends BaseFragment {

    private RecyclerView mRv_pro_msg;
    private ProjectMsgAdapter mProjectMsgAdapter;

    @Override
    public int getViewID() {
        return R.layout.fragment_progect;
    }

    @Override
    public void initView() {
        mRv_pro_msg = (RecyclerView) findViewById(R.id.rv_pro_msg);
    }

    @Override
    public void initListerenAndAdapter() {
        mProjectMsgAdapter = new ProjectMsgAdapter(getContext());
        mRv_pro_msg.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRv_pro_msg.setAdapter(mProjectMsgAdapter);

    }

    @Override
    public void initData() {
        if (mProjectMsgAdapter != null) {
            mProjectMsgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void otherClick(@Nullable View v) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
    }
}
