package com.tix.concor.remote.domain;

import com.tix.concor.core.framework.JoinEvent;
import com.tix.concor.core.framework.TaskEvent;

import java.util.List;
import java.util.Map;

public class Stats {
    private Map<String, List<TaskEvent>> tasks;
    private Map<String, List<JoinEvent>> joins;
    private Map<String, Integer> tps;

    public Stats(Map<String, List<TaskEvent>> tasks, Map<String, List<JoinEvent>> joins, Map<String, Integer> tps) {
        this.tasks = tasks;
        this.joins = joins;
        this.tps = tps;
    }

    public Map<String, List<TaskEvent>> getTasks() {
        return tasks;
    }

    public Map<String, List<JoinEvent>> getJoins() {
        return joins;
    }

    public Map<String, Integer> getTps() {
        return tps;
    }
}
