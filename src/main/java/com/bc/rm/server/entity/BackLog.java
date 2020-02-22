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
     * 0:新建
     * 1:进行中
     * 2:已解决
     * 3:测试中
     * 4:已拒绝
     * 5:待讨论
     * 6:待确认
     * 7:待安排
     * 8:测试OK
     * 9:跟进中
     * 10:已关闭
     */
    private String status;
    private String title;
    private String deadLine;
    private String createTime;

    public BackLog() {

    }

    public BackLog(String moduleId, String type, String status, String title, String deadLine) {
        this.id = CommonUtil.generateId();
        this.moduleId = moduleId;
        this.type = type;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
