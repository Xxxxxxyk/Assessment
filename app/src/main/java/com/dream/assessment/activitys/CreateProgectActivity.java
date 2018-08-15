package com.dream.assessment.activitys;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dream.assessment.adapter.HomeAdapter;
import com.dream.assessment.assessment.R;

import com.dream.assessment.base.BaseActivity;
import com.dream.assessment.bean.Project;
import com.dream.assessment.bean.ProjectDao;
import com.dream.assessment.utils.GreenDaoHelper;

import org.jetbrains.annotations.Nullable;

public class CreateProgectActivity extends BaseActivity {

    private TextView mTv_ok;
    private TextView mTv_dismiss;
    private EditText mEt_pro_name;
    private EditText mEt_pro_desc;
    private Long mProgect_group_id;

    @Override
    public int getViewID() {
        return R.layout.activity_create_project;
    }

    @Override
    public void initView() {
        mTv_ok = findViewById(R.id.tv_ok);
        mTv_dismiss = findViewById(R.id.tv_dismiss);
        mEt_pro_name = findViewById(R.id.et_pro_name);
        mEt_pro_desc = findViewById(R.id.et_pro_desc);
    }

    @Override
    public void initListerenAndAdapter() {
        mTv_ok.setOnClickListener(this);
        mTv_dismiss.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mProgect_group_id = getIntent().getLongExtra("PROGECT_GROUP_ID",0);
    }

    @Override
    public void otherClick(@Nullable View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                createProject();
                break;
            case R.id.tv_dismiss:
                finish();
                break;
            default:
                break;
        }
    }

    private void createProject() {

        String name = mEt_pro_name.getText().toString().trim();
        String desc = mEt_pro_desc.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("项目名称不能为空");
        } else {
            ProjectDao projectDao = GreenDaoHelper.getDaoSession().getProjectDao();
            projectDao.insert(new Project((long) (projectDao.loadAll().size() * 100), mProgect_group_id,name,"88888888",desc,System.currentTimeMillis() + ""));
            finish();
        }

    }
}
