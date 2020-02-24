package com.bc.rm.server.entity;

import com.bc.rm.server.util.CommonUtil;

/**
 * 产品待办事项
 *
 * @author zhou
 */
public class BackLog {
    private String id;
    private String epicId;
    private String moduleId;
    private String moduleName;

    /**
     * 事项类别
     * 0:story 1:bug
     */
    private String type;

    // TODO
    // 后期可以做成工作流
    /**
     * 事项状态
     */
    private String statusId;

    private String statusName;
    private String title;
    private String deadLine;
    private String createTime;

    public BackLog() {

    }

    public BackLog(String moduleId, String type, String statusId, String title, String deadLine) {
        this.id = CommonUtil.generateId();
        this.moduleId = moduleId;
        this.type = type;
        this.statusId = statusId;
        this.title = title;
        this.deadLine = deadLine;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getEpicId() {
        return epicId;
    }

    public void setEpicId(String epicId) {
        this.epicId = epicId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
