package com.bc.rm.server.entity;

import java.util.List;

/**
 * 可选项
 *
 * @author zhou
 */
public class Options {
    /**
     * 当前处理人列表
     */
    private List<User> currentUserList;

    /**
     * 状态列表
     */
    private List<Status> statusList;

    /**
     * 模块列表
     */
    private List<Module> moduleList;

    /**
     * 迭代列表
     */
    private List<Sprint> sprintList;

    public List<User> getCurrentUserList() {
        return currentUserList;
    }

    public void setCurrentUserList(List<User> currentUserList) {
        this.currentUserList = currentUserList;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    public List<Sprint> getSprintList() {
        return sprintList;
    }

    public void setSprintList(List<Sprint> sprintList) {
        this.sprintList = sprintList;
    }
}
