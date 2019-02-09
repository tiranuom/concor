package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.*;
import com.tix.concor.core.framework.taskWrapper.TaskWrapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Flow<A> {

    private TaskWrapper<A, A> taskWrapper;
    private String id;
    private FlowManager.ContextSupplier contextSupplier;

    private AtomicInteger sampleCounter = new AtomicInteger(0);

    Flow(TaskWrapper<A, A> taskWrapper, String id) {
        this.taskWrapper = taskWrapper;
        this.id = id;
    }

    public void apply(A a) {
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
        return sampleCounter.incrementAndGet() & 0x00000011;
    }

    public void init(FlowManager.ContextSupplier contextSupplier, Function<JoinType, Join> joinGenerator) {
        this.contextSupplier = contextSupplier;
        taskWrapper.setIndexes(0);
        taskWrapper.init(new JoinAssignmentFunction(joinGenerator));
        System.out.println(taskWrapper);
    }

    public String getId() {
        return id;
    }
}
