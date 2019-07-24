package com.ps.yuyue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ps.lc.utils.log.LogHelper;
import com.ps.yuyue.db.dao.DaoMaster;

/**
 * 类名：com.ps.yuyue.db
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/7/9 16:15
 */
public class DBHelper extends DaoMaster.OpenHelper {
    private static final String TAG = DaoManager.class.getSimpleName();

    public DBHelper(Context context, String dbName) {
        super(context, dbName, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
//----------------------------使用sql实现升级逻辑
        if (oldVersion == newVersion) {
            LogHelper.e("onUpgrade", "数据库是最新版本,无需升级");
            return;
        }
        LogHelper.e("onUpgrade", "数据库从版本" + oldVersion + "升级到版本" + newVersion);
        switch (oldVersion) {
            case 1:
                String sql = "";
                db.execSQL(sql);
            case 2:
            default:
                break;
        }
    }
}
