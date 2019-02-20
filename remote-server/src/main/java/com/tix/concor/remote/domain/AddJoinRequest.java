package com.tix.concor.remote.domain;

import com.tix.concor.core.framework.JoinTarget;
import com.tix.concor.core.framework.JoinType;

public class AddJoinRequest {

    private String flowId;
    private String taskId;
    private JoinType joinType;
    private int threadCount;
    private JoinTarget joinTarget = JoinTarget.PRIMARY;

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

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setJoinTarget(JoinTarget joinTarget) {
        this.joinTarget = joinTarget;
    }

    public JoinTarget getJoinTarget() {
        return joinTarget;
    }
}
