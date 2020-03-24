package com.ps.yuyue.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类名：com.ps.yuyue.db.entity
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/7/9 15:50
 */
@Entity
public class UserEntity {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "NAME")
    private String name;

    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 2146553439)
    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
