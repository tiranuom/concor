package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.*;
import com.tix.concor.core.framework.taskWrapper.TaskWrapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Flow<A> {

    private TaskWrapper<A, A> taskWrapper;
    private String id;
    private FlowManager.ContextSupplier contextSupplier;

    private AtomicInteger sampleCounter = new AtomicInteger(0);

    private AtomicInteger tpsCounter = new AtomicInteger(0);
    private int lastTps;

    Flow(TaskWrapper<A, A> taskWrapper, String id) {
        this.taskWrapper = taskWrapper;
        this.id = id;
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> lastTps = tpsCounter.getAndSet(0), 1, 1, TimeUnit.SECONDS);
    }

    public void apply(A a) {
        tpsCounter.incrementAndGet();
        if (contextSupplier == null) {
            throw new RuntimeException("AbstractFlow is not properly initialized. Please register the flow in flow manager.");
        }

        taskWrapper.apply(a, contextSupplier.get());
    }

    Join assignJoin(String id, Join join) throws RemoteException {
        try {
            return taskWrapper.assignJoinIfMatched(id, join, null);
        } catch (ConfigurationException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    FlowInfo collectSchema() {
        ArrayList<TaskInfo> taskInfo = new ArrayList<>();
        taskWrapper.collectSchema(taskInfo);
        return new FlowInfo(id, taskInfo);
    }

    int nextIndex() {
        return sampleCounter.incrementAndGet() & 0b11111111;
    }

    public void init(FlowManager.ContextSupplier contextSupplier, BiFunction<JoinType, JoinTarget, Join> joinGenerator) {
        this.contextSupplier = contextSupplier;
        taskWrapper.setIndexes(0);
        taskWrapper.init(new JoinAssignmentFunction(joinGenerator, JoinTarget.PRIMARY));
    }

    public String getId() {
        return id;
    }

    public int getTps() {
        return lastTps;
    }
}
