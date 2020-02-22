package com.bc.rm.server.entity;


import com.bc.rm.server.util.CommonUtil;

/**
 * 模块
 *
 * @author zhou
 */
public class Module {
    private String id;
    private String name;
    private String desc;
    private String createTime;

    public Module() {

    }

    public Module(String name, String desc) {
        this.id = CommonUtil.generateId();
        this.name = name;
        this.desc = desc;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
