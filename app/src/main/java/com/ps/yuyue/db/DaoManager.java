package com.ps.yuyue.db;

import com.ps.lc.utils.AppContextUtil;
import com.ps.yuyue.db.dao.DaoMaster;
import com.ps.yuyue.db.dao.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 类名：com.ps.yuyue.db
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/7/9 16:13
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "name.db";
    private static DaoManager mInstance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public static DaoManager getInstance() {
        if (mInstance == null) {
            synchronized (DaoManager.class) {
                if (mInstance == null) {
                    mInstance = new DaoManager();
                }
            }
        }
        return mInstance;
    }

    private DaoManager() {
        if (mInstance == null) {
            DBHelper helper = new DBHelper(AppContextUtil.getAppContext(), DB_NAME);
            Database db = helper.getWritableDb();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(boolean debug) {
        QueryBuilder.LOG_SQL = debug;
        QueryBuilder.LOG_VALUES = debug;
    }
}
