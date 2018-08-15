package com.dream.assessment.utils;

import android.database.sqlite.SQLiteDatabase;

import com.dream.assessment.App;
import com.dream.assessment.bean.DaoMaster;
import com.dream.assessment.bean.DaoSession;

public class GreenDaoHelper {

    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase mDb;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    /**
     * 设置greenDao
     */
    public static void initDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(App.mContext, "project_db", null);
        mDb = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        mDaoSession.clear();
        return mDaoSession;
    }
    public static SQLiteDatabase getDb() {
        return mDb;
    }
}
