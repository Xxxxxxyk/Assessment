package com.dream.assessment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dream.assessment.assessment.R;
import com.dream.assessment.bean.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {

    private final List<Project> progectList;
    private final Context context;
    private OnItemClickListener mItemClickListener;

    public ProjectAdapter(List<Project> progectList, Context context) {
        this.progectList = progectList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProjectAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_list, null));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.mTv_pro_name.setText(progectList.get(i).getName());
        myViewHolder.mTv_pro_id.setText("ID:" + progectList.get(i).getProgect_id());
        if(mItemClickListener != null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return progectList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView mTv_pro_name;
        private final TextView mTv_pro_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv_pro_name = itemView.findViewById(R.id.tv_pro_name);
            mTv_pro_id = itemView.findViewById(R.id.tv_pro_id);
        }
    }
}
