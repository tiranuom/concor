package com.tix.concor.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class SniffingContext extends Context {

    private long lastTaskUpdatedNano = System.nanoTime(), lastJoinUpdatedNano = System.nanoTime();
    private List<JoinEvent> joinEvents = new ArrayList<>();
    private List<TaskEvent> taskEvents = new ArrayList<>();
    private BiConsumer<List<JoinEvent>, List<TaskEvent>> completeHandler;

    public SniffingContext(BiConsumer<List<JoinEvent>, List<TaskEvent>> completeHandler) {
        this.completeHandler = completeHandler;
    }

    @Override
    public void hitJoin(Join join) {
        long currentNano = System.nanoTime();
        String id = join.getJoinId();
        long bufferSize = join.getBufferSize();
        long latency = currentNano - lastJoinUpdatedNano;

        joinEvents.add(new JoinEvent(id, join.getTaskId(), latency, bufferSize, join.getJoinType()));
        lastJoinUpdatedNano = currentNano;
    }

    @Override
    public void hitTask(String id) {
        long currentNano = System.nanoTime();
        long latency = currentNano - lastTaskUpdatedNano;

        taskEvents.add(new TaskEvent(id, latency));
        this.lastTaskUpdatedNano = currentNano;
    }



    @Override
    public void complete() {
        completeHandler.accept(joinEvents, taskEvents);
    }

    public static class JoinEvent {

        private String id;
        private String taskId;
        private long latency;
        private long bufferSize;
        private Join.JoinType joinType;

        private JoinEvent(String id, String taskId, long latency, long bufferSize, Join.JoinType joinType) {
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

        public Join.JoinType getJoinType() {
            return joinType;
        }
    }

    public static class TaskEvent {

        private String id;
        private long latency;

        private TaskEvent(String id, long latency) {
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

    enum EventType {
        JOIN, TASK
    }
}
