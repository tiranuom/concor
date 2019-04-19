package com.tix.concor.core.framework;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class JoinEvent implements Serializable {

    private String id;
    private String taskId;
    private long latency;
    private long bufferSize;
    private long activeThreadCount;
    private JoinType joinType;

    JoinEvent(String id, String taskId, long latency, long bufferSize, long activeThreadCount, JoinType joinType) {
        this.id = id;
        this.taskId = taskId;
        this.latency = latency;
        this.bufferSize = bufferSize;
        this.activeThreadCount = activeThreadCount;
        this.joinType = joinType;
    }

    public EventType eventType() {
        return EventType.JOIN;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getId() {
        return id;
    }

    public long getLatency() {
        return latency;
    }

    public long getBufferSize() {
        return bufferSize;
    }

    public long getActiveThreadCount() {
        return activeThreadCount;
    }

    public static JoinEvent merge(List<JoinEvent> joinEvents) {
        JoinEvent first = joinEvents.get(0);

        long totalLatancy = 0, totalBufferSize = 0, totalActiveThreadCount = 0;

        for (JoinEvent joinEvent : joinEvents) {
            assert Objects.equals(first.id, joinEvent.id);

            totalLatancy += joinEvent.latency;
            totalBufferSize += joinEvent.bufferSize;
            totalActiveThreadCount += joinEvent.activeThreadCount;
        }

        int size = joinEvents.size();

        return new JoinEvent(first.id,
                first.taskId,
                totalLatancy / size,
                totalBufferSize / size,
                totalActiveThreadCount / size,
                first.joinType);
    }

    public JoinType getJoinType() {
        return joinType;
    }
}
