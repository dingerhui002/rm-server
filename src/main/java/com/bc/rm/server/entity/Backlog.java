package com.bc.rm.server.entity;

import com.bc.rm.server.util.CommonUtil;

/**
 * 产品待办事项
 *
 * @author zhou
 */
public class Backlog {
    private String id;

    private String title;

    /**
     * 事项状态
     */
    private String statusId;
    private String statusName;

    private String currentUserId;
    private String currentUserName;

    private String moduleId;
    private String moduleName;

    private String sprintId;
    private String sprintName;

    private String priorityOrder;
    private String priority;
    private String importance;

    /**
     * 事项类别
     * 0:story 1:bug
     */
    private String type;

    private String deadLine;
    private String createTime;

    public Backlog() {

    }

    public Backlog(String title,
                   String statusId,
                   String currentUserId,
                   String moduleId,
                   String sprintId,
                   String priorityOrder,
                   String priority,
                   String importance,
                   String deadLine) {
        this.id = CommonUtil.generateId();
        this.title = title;
        this.statusId = statusId;
        this.currentUserId = currentUserId;
        this.moduleId = moduleId;
        this.sprintId = sprintId;
        this.priorityOrder = priorityOrder;
        this.priority = priority;
        this.importance = importance;
        this.deadLine = deadLine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
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

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(String priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Backlog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", statusId='" + statusId + '\'' +
                ", statusName='" + statusName + '\'' +
                ", currentUserId='" + currentUserId + '\'' +
                ", currentUserName='" + currentUserName + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", sprintId='" + sprintId + '\'' +
                ", sprintName='" + sprintName + '\'' +
                ", priorityOrder='" + priorityOrder + '\'' +
                ", priority='" + priority + '\'' +
                ", importance='" + importance + '\'' +
                ", type='" + type + '\'' +
                ", deadLine='" + deadLine + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
