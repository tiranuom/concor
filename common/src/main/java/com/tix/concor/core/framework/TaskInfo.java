package com.tix.concor.core.framework;

public class TaskInfo {
    private String id;
    private String type;

    public TaskInfo(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {

        return id;
    }

    public String getType() {
        return type;
    }
}
