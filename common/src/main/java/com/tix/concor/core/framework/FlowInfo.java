package com.tix.concor.core.framework;

import java.util.List;

public class FlowInfo {

    private String id;
    private List<TaskInfo> taskInfo;

    public FlowInfo(String id, List<TaskInfo> taskInfo) {
        this.id = id;
        this.taskInfo = taskInfo;
    }

    public String getId() {
        return id;
    }

    public List<TaskInfo> getTaskInfo() {
        return taskInfo;
    }
}
