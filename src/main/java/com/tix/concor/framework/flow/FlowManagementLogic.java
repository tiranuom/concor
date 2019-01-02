package com.tix.concor.framework.flow;

import com.tix.concor.framework.Join;
import com.tix.concor.framework.SniffingContext;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public abstract class FlowManagementLogic {

    private Map<String, Flow> flowMap = new HashMap<>();
    private int initialBufferSize;
    private int counter = 0;

    public abstract void onStats(List<SniffingContext.JoinEvent> joinStats, List<SniffingContext.TaskEvent> taskStats, int nextIndex, Flow flow);

    public void split(Flow flow, String taskId, Join join) {
        if (flow != null) {
            flow.assignJoin(taskId, join);
        }
    }

    public Join join(Flow flow, String taskId) {
        if (flow != null) {
            return flow.assignJoin(taskId, null);
        }
        return null;
    }

    public void addSingleThreadJoin(String flowId, String taskId) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = new Join(initialBufferSize, Executors.newSingleThreadExecutor(), (flowId + ":" + ++counter), Join.JoinType.SINGLE_THREADED);
        join.init();
        flow.assignJoin(taskId, join);
    }

    public void addCachedThreadJoin(String flowId, String taskId) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = new Join(initialBufferSize, Executors.newCachedThreadPool(), (flowId + ":" + ++counter), Join.JoinType.CACHED);
        join.init();
        flow.assignJoin(taskId, join);
    }

    public void addMultiThreadJoin(String flowId, String taskId, int threadCount) throws RuntimeException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = new Join(initialBufferSize, Executors.newFixedThreadPool(threadCount), (flowId + ":" + ++counter), Join.JoinType.MULTI_THREADED);
        join.init();
        flow.assignJoin(taskId, join);
    }

    public void addFlow(String id, Flow flow) {
        flowMap.put(id, flow);
    }

    public Flow removeFlow(String id) {
        return flowMap.remove(id);
    }

    public void setInitialBufferSize(int initialBufferSize) {
        this.initialBufferSize = initialBufferSize;
    }
}
