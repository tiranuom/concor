package com.tix.concor.remote.domain;

import com.tix.concor.core.framework.JoinType;

public class RemoveJoinRequest {

    private String flowId;
    private String taskId;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
