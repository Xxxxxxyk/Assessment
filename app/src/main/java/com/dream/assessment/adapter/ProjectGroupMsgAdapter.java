package com.dream.assessment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dream.assessment.assessment.R;
import com.dream.assessment.bean.ProjectGroup;
import com.dream.assessment.bean.ProjectGroupDao;
import com.dream.assessment.utils.GreenDaoHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectGroupMsgAdapter extends RecyclerView.Adapter<ProjectGroupMsgAdapter.ViewHolder> {

    private final Context context;
    private final List<ProjectGroup> mProjectGroups = new ArrayList<>();

    public ProjectGroupMsgAdapter(Context context) {
        this.context = context;
        mProjectGroups.clear();
        mProjectGroups.addAll(GreenDaoHelper.getDaoSession().getProjectGroupDao().loadAll());
    }

    @NonNull
    @Override
    public ProjectGroupMsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProjectGroupMsgAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_progroup_msg_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectGroupMsgAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mTv_msg.setText("您已经成功建立名为\"" + mProjectGroups.get(i).getName() + "\"的项目组");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        viewHolder.mTv_msg_time.setText(simpleDateFormat.format(new Date(Long.parseLong(mProjectGroups.get(i).getTime()))));
    }

    @Override
    public int getItemCount() {
        return mProjectGroups.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_msg;
        private final TextView mTv_msg_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv_msg = itemView.findViewById(R.id.tv_msg);
            mTv_msg_time = itemView.findViewById(R.id.tv_msg_time);
        }
    }
}
