package com.tix.concor.core.framework;

import java.util.List;
import java.util.Objects;

public class TaskEvent {

    private String id;
    private long latency;

    TaskEvent(String id, long latency) {
        this.id = id;
        this.latency = latency;
    }

    public EventType eventType() {
        return EventType.TASK;
    }

    public String getId() {
        return id;
    }

    public long getLatency() {
        return latency;
    }

    public static TaskEvent merge(List<TaskEvent> joinEvents) {
        TaskEvent first = joinEvents.get(0);

        long totalLatancy = 0;

        for (TaskEvent joinEvent : joinEvents) {
            assert Objects.equals(first.id, joinEvent.id);

            totalLatancy += joinEvent.latency;
        }

        return new TaskEvent(first.id, totalLatancy / joinEvents.size());
    }
}
