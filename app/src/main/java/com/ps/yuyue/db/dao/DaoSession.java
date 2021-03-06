package com.ps.yuyue.db.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ps.yuyue.db.entity.StudentEntity;
import com.ps.yuyue.db.entity.UserEntity;

import com.ps.yuyue.db.dao.StudentEntityDao;
import com.ps.yuyue.db.dao.UserEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig studentEntityDaoConfig;
    private final DaoConfig userEntityDaoConfig;

    private final StudentEntityDao studentEntityDao;
    private final UserEntityDao userEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        studentEntityDaoConfig = daoConfigMap.get(StudentEntityDao.class).clone();
        studentEntityDaoConfig.initIdentityScope(type);

        userEntityDaoConfig = daoConfigMap.get(UserEntityDao.class).clone();
        userEntityDaoConfig.initIdentityScope(type);

        studentEntityDao = new StudentEntityDao(studentEntityDaoConfig, this);
        userEntityDao = new UserEntityDao(userEntityDaoConfig, this);

        registerDao(StudentEntity.class, studentEntityDao);
        registerDao(UserEntity.class, userEntityDao);
    }
    
    public void clear() {
        studentEntityDaoConfig.clearIdentityScope();
        userEntityDaoConfig.clearIdentityScope();
    }

    public StudentEntityDao getStudentEntityDao() {
        return studentEntityDao;
    }

    public UserEntityDao getUserEntityDao() {
        return userEntityDao;
    }

}
