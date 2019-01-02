package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.Join;
import com.tix.concor.core.framework.taskWrapper.TaskWrapper;

import java.util.concurrent.atomic.AtomicInteger;

public class Flow<A> {

    private TaskWrapper<A, A> taskWrapper;
    private String id;
    private FlowManager.ContextSupplier contextSupplier;

    private AtomicInteger sampleCounter = new AtomicInteger(0);

    Flow(TaskWrapper<A, A> taskWrapper, String id) {
        this.taskWrapper = taskWrapper;
        this.id = id;
    }

    public void apply(A a) throws Exception {
        if (contextSupplier == null) {
            throw new Exception("AbstractFlow is not properly initialized. Please register the flow in flow manager.");
        }

        taskWrapper.apply(a, contextSupplier.get());
    }

    Join assignJoin(String id, Join join) {
        return taskWrapper.assignJoinIfMatched(id, join);
    }

    int nextIndex() {
        return sampleCounter.incrementAndGet();
    }

    public void init(FlowManager.ContextSupplier contextSupplier) {
        this.contextSupplier = contextSupplier;
        taskWrapper.setIndexes(0);
    }

    public String getId() {
        return id;
    }
}
