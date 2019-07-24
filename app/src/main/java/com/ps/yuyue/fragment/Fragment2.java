package com.ps.yuyue.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.ps.lc.utils.ListUtils;
import com.ps.lc.utils.log.LogHelper;
import com.ps.yuyue.R;
import com.ps.yuyue.db.DaoManager;
import com.ps.yuyue.db.dao.UserEntityDao;
import com.ps.yuyue.db.entity.StudentEntity;
import com.ps.yuyue.db.entity.UserEntity;

import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;

/**
 * 类名：com.ps.yuyue.fragment
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 17:02
 */
public class Fragment2 extends BaseAbsFragment {
    public static Fragment2 create() {
        return new Fragment2();
    }

    @Override
    protected int layout() {
        return R.layout.frag_db_operation;
    }

    @Override
    protected void initView(Bundle savedInstanceState, ViewGroup containerLay) {

    }

    @OnClick({R.id.insert, R.id.update, R.id.delete, R.id.query})
    public void onEventClick(View view) {
        switch (view.getId()) {
            case R.id.insert:
                createStudent();
                break;
            case R.id.update:
                updateStudent();
                break;
            case R.id.delete:

                break;
            case R.id.query:
                queryStudent();
                break;
            default:
        }
    }

    private void createStudent(){
        UserEntity user = new UserEntity();
        user.setName("流程");
        DaoManager.getInstance().getDaoSession().getUserEntityDao().insert(user);
    }

    private void updateStudent(){
        List<UserEntity> list = queryStudent();
        if(ListUtils.isNotEmpty(list)){
            for(UserEntity entity : list){
                entity.setName(entity.getName()+"11");
            }
        }
        DaoManager.getInstance().getDaoSession().getUserEntityDao().updateInTx(list.toArray(new UserEntity[list.size()]));
    }

    private List<UserEntity> queryStudent(){
        List<UserEntity> list =  DaoManager.getInstance().getDaoSession().getUserEntityDao().loadAll();
        DaoManager.getInstance().getDaoSession().getUserEntityDao().queryBuilder().list();
        LogHelper.i(list.toString());
        return list;
    }
}
