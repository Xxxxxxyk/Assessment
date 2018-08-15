package com.dream.assessment.activitys;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dream.assessment.base.BaseActivity;
import com.dream.assessment.assessment.R;
import com.dream.assessment.bean.ProjectGroup;
import com.dream.assessment.bean.ProjectGroupDao;
import com.dream.assessment.utils.GreenDaoHelper;

import org.jetbrains.annotations.Nullable;

public class JoinActivity extends BaseActivity {

    private TextView mTv_dismiss;
    private TextView mTv_ok;
    private EditText mEt_pro_name;

    @Override
    public int getViewID() {
        return R.layout.activity_join;
    }

    @Override
    public void initView(){
        mTv_dismiss = findViewById(R.id.tv_dismiss);
        mTv_ok = findViewById(R.id.tv_ok);
        mEt_pro_name = findViewById(R.id.et_pro_name);
    }

    @Override
    public void initListerenAndAdapter() {
        mTv_dismiss.setOnClickListener(this);
        mTv_ok.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void otherClick(@Nullable View v) {
        switch (v.getId()) {
            case R.id.tv_dismiss:
                finish();
                break;
            case R.id.tv_ok:
                createProgect();
                break;
            default:
                break;
        }
    }

    //创建成功存入数据库
    private void createProgect() {

        String pro_name = mEt_pro_name.getText().toString().trim();

        if (TextUtils.isEmpty(pro_name)) {
            ToastUtils.showShort("项目组名称不能为空");
        } else {
            ProjectGroupDao groupDao = GreenDaoHelper.getDaoSession().getProjectGroupDao();
            groupDao.insert(new ProjectGroup(null, pro_name,"88888888","JunChen",System.currentTimeMillis() + ""));
            finish();
        }

    }
}