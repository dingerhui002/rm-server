package com.bc.rm.server.entity;

import com.bc.rm.server.cons.Constant;
import com.bc.rm.server.util.CommonUtil;

/**
 * 用户
 *
 * @author zhou
 */
public class User {
    private String id;
    private String name;
    private String phone;
    private String mail;
    private String desc;

    /**
     * "0" : 启用
     * "1" : 停用
     */
    private String status;

    private String createTime;
    private String lastLoginTime;

    public User() {

    }

    public User(String name, String phone, String mail, String desc) {
        this.id = CommonUtil.generateId();
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.desc = desc;
        this.status = Constant.USER_STATUS_ACTIVATE;
        this.createTime = CommonUtil.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
