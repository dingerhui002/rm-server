package com.bc.rm.server.entity;

import com.bc.rm.server.util.CommonUtil;

/**
 * 迭代
 *
 * @author zhou
 */
public class Sprint {
    private String id;
    private String name;
    private String desc;
    private String beginDate;
    private String endDate;
    private String createTime;
    private String updateTime;

    public Sprint() {

    }

    public Sprint(String name, String desc, String beginDate, String endDate) {
        this.id = CommonUtil.generateId();
        this.name = name;
        this.desc = desc;
        this.beginDate = beginDate;
        this.endDate = endDate;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
