package com.dream.assessment.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dream.assessment.adapter.ChatLVAdapter;
import com.dream.assessment.adapter.FaceGVAdapter;
import com.dream.assessment.adapter.FaceVPAdapter;
import com.dream.assessment.assessment.R;
import com.dream.assessment.bean.ChatInfo;
import com.dream.assessment.bean.Project;
import com.dream.assessment.bean.ProjectDao;
import com.dream.assessment.bean.ProjectMsg;
import com.dream.assessment.bean.ProjectMsgDao;
import com.dream.assessment.utils.GreenDaoHelper;
import com.dream.assessment.views.DropdownListView;
import com.dream.assessment.views.MyEditText;


public class QQActivity extends Activity implements OnClickListener, DropdownListView.OnRefreshListenerHeader {
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int IMAGE_REQUEST_CODE = 200;
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private MyEditText input;
    private Button send;
    private DropdownListView mListView;
    private ChatLVAdapter mLvAdapter;

    private LinearLayout chat_face_container;
    private ImageView image_face;//表情图标
    // 7列3行
    private int columns = 6;
    private int rows = 4;
    private List<View> views = new ArrayList<View>();
    private List<String> staticFacesList;
    private LinkedList<ChatInfo> infos = new LinkedList<ChatInfo>();
    private SimpleDateFormat sd;

    private String reply = "";//模拟回复
    private ImageView mIv_add_pic;
    private long mProject_msg_id;
    private ProjectMsgDao mProjectMsgDao;

    @SuppressLint("SimpleDateFormat")
    private void initViews() {
        mListView = (DropdownListView) findViewById(R.id.message_chat_listview);
        sd = new SimpleDateFormat("MM-dd HH:mm");

        //表情图标
        image_face = (ImageView) findViewById(R.id.image_face);
        //表情布局
        chat_face_container = (LinearLayout) findViewById(R.id.chat_face_container);
        mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
        mIv_add_pic = findViewById(R.id.iv_add_pic);
        mIv_add_pic.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new PageChange());
        //表情下小圆点
        mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
        input = (MyEditText) findViewById(R.id.input_sms);
        input.setOnClickListener(this);
        send = (Button) findViewById(R.id.send_sms);
        InitViewPager();
        //表情按钮
        image_face.setOnClickListener(this);
        // 发送
        send.setOnClickListener(this);

        mListView.setOnRefreshListenerHead(this);
        mListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    if (chat_face_container.getVisibility() == View.VISIBLE) {
                        chat_face_container.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 初始化数据库中的消息
     */
    private void initMsgs() {
        ProjectDao projectDao = GreenDaoHelper.getDaoSession().getProjectDao();
        Project project = projectDao.load(mProject_msg_id);
        if (project != null) {
            List<ProjectMsg> progectMsgList = new ArrayList<>();
            progectMsgList.addAll(project.getProgectMsgList());
            for (int i = 0; i < progectMsgList.size(); i++) {
                if (TextUtils.equals("0", progectMsgList.get(i).getFrom_or_to())) {
                    infos.add(new ChatInfo(progectMsgList.get(i).getMsg_content(), progectMsgList.get(i).getMsg_time(), 0));
                } else if(TextUtils.equals("1", progectMsgList.get(i).getFrom_or_to())){
                    infos.add(new ChatInfo(progectMsgList.get(i).getMsg_content(), progectMsgList.get(i).getMsg_time(), 1));
                }else{
                    infos.add(new ChatInfo(progectMsgList.get(i).getMsg_content(), progectMsgList.get(i).getMsg_time(), 2));
                }
            }
            mListView.setSelection(infos.size() - 1);
        }
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.input_sms://输入框
                if (chat_face_container.getVisibility() == View.VISIBLE) {
                    chat_face_container.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_add_pic:
                openPic();
                break;
            case R.id.image_face://表情
                hideSoftInputView();//隐藏软键盘
                if (chat_face_container.getVisibility() == View.GONE) {
                    chat_face_container.setVisibility(View.VISIBLE);
                } else {
                    chat_face_container.setVisibility(View.GONE);
                }
                break;
            case R.id.send_sms://发送
                reply = input.getText().toString();
                if (!TextUtils.isEmpty(reply)) {
                    infos.add(getChatInfoTo(reply));
                    mLvAdapter.setList(infos);
                    mLvAdapter.notifyDataSetChanged();
                    mListView.setSelection(infos.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            infos.add(getChatInfoFrom(reply));
                            mLvAdapter.setList(infos);
                            mLvAdapter.notifyDataSetChanged();
                            mListView.setSelection(infos.size() - 1);
                        }
                    }, 1000);
                    input.setText("");
                }
                break;
        }
    }

    /**
     * 打开相册
     */
    private void openPic() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    /**
     * 获取选取的照片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();

                        infos.add(getImageInfoTo(path));
//                        infos.add(getChatInfoTo(reply));
                        mLvAdapter.setList(infos);
                        mLvAdapter.notifyDataSetChanged();
                        mListView.setSelection(infos.size() - 1);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /*
     * 初始表情 *
     */
    private void InitViewPager() {
        // 获取页数
        for (int i = 0; i < getPagerCount(); i++) {
            views.add(viewPagerItem(i));
            LayoutParams params = new LayoutParams(16, 16);
            mDotsLayout.addView(dotsItem(i), params);
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
        mViewPager.setAdapter(mVpAdapter);
        mDotsLayout.getChildAt(0).setSelected(true);
    }

    private View viewPagerItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
        GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
        /**
         *  每一页末尾都有一个删除图标，columns *　rows　－　1; 空出最后一个位置给删除图标
         * */
        List<String> subList = new ArrayList<String>();
        subList.addAll(staticFacesList
                .subList(position * (columns * rows - 1),
                        (columns * rows - 1) * (position + 1) > staticFacesList
                                .size() ? staticFacesList.size() : (columns
                                * rows - 1)
                                * (position + 1)));
        /**
         * 末尾添加删除图标
         * */
        subList.add("emotion_del_normal.png");
        FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
        gridview.setAdapter(mGvAdapter);
        gridview.setNumColumns(columns);
        // 单击表情执行的操作
        gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
                    if (!png.contains("emotion_del_normal")) {// 如果不是删除图标
                        insert(getFace(png));
                    } else {
                        delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return gridview;
    }

    private SpannableStringBuilder getFace(String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            /**
             * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
             * 所以这里对这个tempText值做特殊处理
             * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
             * */
            String tempText = "#[" + png + "]#";
            sb.append(tempText);
            sb.setSpan(
                    new ImageSpan(QQActivity.this, BitmapFactory
                            .decodeStream(getAssets().open(png))), sb.length()
                            - tempText.length(), sb.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb;
    }

    /**
     * 向输入框里添加表情
     */
    private void insert(CharSequence text) {
        int iCursorStart = Selection.getSelectionStart((input.getText()));
        int iCursorEnd = Selection.getSelectionEnd((input.getText()));
        if (iCursorStart != iCursorEnd) {
            ((Editable) input.getText()).replace(iCursorStart, iCursorEnd, "");
        }
        int iCursor = Selection.getSelectionEnd((input.getText()));
        ((Editable) input.getText()).insert(iCursor, text);
    }

    /**
     * 删除图标执行事件
     * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
     */
    private void delete() {
        if (input.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(input.getText());
            int iCursorStart = Selection.getSelectionStart(input.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        String st = "#[face/png/f_static_000.png]#";
                        ((Editable) input.getText()).delete(
                                iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        ((Editable) input.getText()).delete(iCursorEnd - 1,
                                iCursorEnd);
                    }
                } else {
                    ((Editable) input.getText()).delete(iCursorStart,
                            iCursorEnd);
                }
            }
        }
    }

    /**
     * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
     **/
    private boolean isDeletePng(int cursor) {
        String st = "#[face/png/f_static_000.png]#";
        String content = input.getText().toString().substring(0, cursor);
        if (content.length() >= st.length()) {
            String checkStr = content.substring(content.length() - st.length(),
                    content.length());
            String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    private ImageView dotsItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dot_image, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
        iv.setId(position);
        return iv;
    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return
     */
    private int getPagerCount() {
        int count = staticFacesList.size();
        return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
                : count / (columns * rows - 1) + 1;
    }

    /**
     * 初始化表情列表staticFacesList
     */
    private void initStaticFaces() {
        try {
            staticFacesList = new ArrayList<String>();
            String[] faces = getAssets().list("face/png");
            //将Assets中的表情名称转为字符串一一添加进staticFacesList
            for (int i = 0; i < faces.length; i++) {
                staticFacesList.add(faces[i]);
            }
            //去掉删除图片
            staticFacesList.remove("emotion_del_normal.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 表情页改变时，dots效果也要跟着改变
     */
    class PageChange implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
                mDotsLayout.getChildAt(i).setSelected(false);
            }
            mDotsLayout.getChildAt(arg0).setSelected(true);
        }

    }

    /**
     * 发送的信息
     *
     * @param message
     * @return
     */
    private ChatInfo getChatInfoTo(String message) {
        ChatInfo info = new ChatInfo(message, sd.format(new Date()), 1);
        mProjectMsgDao.insert(new ProjectMsg(null, mProject_msg_id, "1", info.time, info.content));
        return info;
    }

    /**
     * 发送图片
     */
    private ChatInfo getImageInfoTo(String message) {
        ChatInfo info = new ChatInfo(message, sd.format(new Date()), 2);
        mProjectMsgDao.insert(new ProjectMsg(null, mProject_msg_id, "2", info.time, info.content));
        return info;
    }

    /**
     * 接收的信息
     *
     * @param message
     * @return
     */
    private ChatInfo getChatInfoFrom(String message) {
        ChatInfo info = new ChatInfo(message, sd.format(new Date()), 0);
        mProjectMsgDao.insert(new ProjectMsg(null, mProject_msg_id, "0", info.time, info.content));
        return info;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mLvAdapter.setList(infos);
                    mLvAdapter.notifyDataSetChanged();
                    mListView.onRefreshCompleteHeader();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);
        initStaticFaces();
        initViews();
        checkPermisson();
        initData();
    }

    private void initData() {
        mProject_msg_id = getIntent().getLongExtra("PROJECT_MSG_ID", 0);
        LogUtils.e("传送过来的MSG的ID是:" + mProject_msg_id);
        mProjectMsgDao = GreenDaoHelper.getDaoSession().getProjectMsgDao();

        //初始化数据库中的消息
        initMsgs();


        if (infos.size() == 0) {
            infos.add(getChatInfoFrom("你好啊！"));
        }
        mLvAdapter = new ChatLVAdapter(this, infos);
        mListView.setAdapter(mLvAdapter);
        mListView.setSelection(infos.size() - 1);
    }

    @Override
    public void onRefresh() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Message msg = mHandler.obtainMessage(0);
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean checkPermisson(){
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ToastUtils.showShort("拒绝权限会导致图片无法显示");
                ActivityCompat.requestPermissions(this, permissions, 321);
                return false;
            }
        }
        return true;
    }

}
