package com.tix.concor.common;

import java.util.List;
import java.util.Objects;

public class JoinEvent {

    private String id;
    private String taskId;
    private long latency;
    private long bufferSize;
    private JoinType joinType;

    JoinEvent(String id, String taskId, long latency, long bufferSize, JoinType joinType) {
        this.id = id;
        this.taskId = taskId;
        this.latency = latency;
        this.bufferSize = bufferSize;
        this.joinType = joinType;
    }

    public EventType eventType() {
        return EventType.JOIN;
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

    public static JoinEvent merge(List<JoinEvent> joinEvents) {
        JoinEvent first = joinEvents.get(0);

        long totalLatancy = 0, totalBufferSize = 0;

        for (JoinEvent joinEvent : joinEvents) {
            assert Objects.equals(first.id, joinEvent.id);

            totalLatancy += joinEvent.latency;
            totalBufferSize += joinEvent.bufferSize;
        }

        return new JoinEvent(first.id, first.taskId, totalLatancy / joinEvents.size(), totalBufferSize / joinEvents.size(), first.joinType);
    }

    public JoinType getJoinType() {
        return joinType;
    }
}
