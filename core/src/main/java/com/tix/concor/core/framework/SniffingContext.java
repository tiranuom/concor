package com.tix.concor.core.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SniffingContext extends Context {

    private long lastTaskUpdatedNano = System.nanoTime(), lastJoinUpdatedNano = System.nanoTime();
    private List<JoinEvent> joinEvents = new ArrayList<>();
    private List<TaskEvent> taskEvents = new ArrayList<>();
    private BiConsumer<List<JoinEvent>, List<TaskEvent>> completeHandler;
    private String previousTaskId = "", previousJoinId = "";

    public SniffingContext(BiConsumer<List<JoinEvent>, List<TaskEvent>> completeHandler) {
        this.completeHandler = completeHandler;
    }

    @Override
    public void hitJoin(Join join) {
        long currentNano = System.nanoTime();
        if (join != null) {
            String id = join.getJoinId();
            long bufferSize = join.getBufferSize();
            long latency = currentNano - lastJoinUpdatedNano;

            joinEvents.add(new JoinEvent(id, join.getTaskId(), latency, bufferSize, join.getJoinType()));
            this.previousJoinId = id;
            lastJoinUpdatedNano = currentNano;
        } else {
//            lastJoinUpdatedNano = currentNano;
        }
    }

    @Override
    public void hitTask(String id) {
        long currentNano = System.nanoTime();
        long latency = currentNano - lastTaskUpdatedNano;

        taskEvents.add(new TaskEvent(previousTaskId, latency));
        this.previousTaskId = id;
        this.lastTaskUpdatedNano = currentNano;
    }



    @Override
    public void complete() {
        completeHandler.accept(joinEvents, taskEvents);
    }

}
