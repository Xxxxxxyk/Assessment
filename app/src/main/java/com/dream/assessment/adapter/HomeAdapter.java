package com.dream.assessment.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dream.assessment.activitys.CreateProgectActivity;
import com.dream.assessment.activitys.QQActivity;
import com.dream.assessment.assessment.R;
import com.dream.assessment.bean.Project;
import com.dream.assessment.bean.ProjectGroup;
import com.dream.assessment.bean.ProjectGroupDao;
import com.dream.assessment.bean.ProjectGroupImpl;
import com.dream.assessment.utils.GreenDaoHelper;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private final List<ProjectGroupImpl> list;
    private final Context context;

    private ProjectAdapter mProjectadapter;



    public HomeAdapter(Context context, List<ProjectGroupImpl> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.mTv_project_name.setText(list.get(i).name);
        myViewHolder.mTv_project_id.setText("ID:" + list.get(i).progect_group_id);
        myViewHolder.mTv_project_admin.setText("管理员:" + list.get(i).admin);
        if (list.get(i).flag) {
            myViewHolder.mLl_add_project.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.mLl_add_project.setVisibility(View.GONE);
        }


        myViewHolder.mLl_add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_view = new Intent(context, CreateProgectActivity.class);
                intent_view.putExtra("PROGECT_GROUP_ID",list.get(i).id);
                context.startActivity(intent_view);
            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (list.get(i).flag) {
                    list.get(i).flag = false;
                }else{
                    list.get(i).flag = true;
                }
                final List<Project> progectList = new ArrayList<>();
                ProjectGroupDao projectGroupDao = GreenDaoHelper.getDaoSession().getProjectGroupDao();
                ProjectGroup projectGroup = projectGroupDao.load(list.get(i).id);
                progectList.addAll(projectGroup.getProgectList());

                mProjectadapter = new ProjectAdapter(progectList,context);
                myViewHolder.mRv_home_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                myViewHolder.mRv_home_list.setAdapter(mProjectadapter);

                mProjectadapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent view = new Intent(context,QQActivity.class);
                        LogUtils.e(progectList.toString() + "      " + position);
                        view.putExtra("PROJECT_MSG_ID",progectList.get(position).get_id());
                        context.startActivity(view);
                    }
                });

                LogUtils.e(progectList.toString() + progectList.size() + "");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_project_name;
        private final TextView mTv_project_id;
        private final TextView mTv_project_admin;
        private final LinearLayout mLl_add_project;
        private final RecyclerView mRv_home_list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv_project_name = itemView.findViewById(R.id.tv_project_name);
            mTv_project_id = itemView.findViewById(R.id.tv_project_id);
            mTv_project_admin = itemView.findViewById(R.id.tv_project_admin);
            mLl_add_project = itemView.findViewById(R.id.ll_add_project);
            mRv_home_list = itemView.findViewById(R.id.rv_home_list);
        }

    }

}