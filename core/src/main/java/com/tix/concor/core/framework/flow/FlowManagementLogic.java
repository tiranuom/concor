package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.Join;
import com.tix.concor.common.JoinEvent;
import com.tix.concor.common.JoinType;
import com.tix.concor.common.TaskEvent;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public abstract class FlowManagementLogic {

    private Map<String, Flow> flowMap = new HashMap<>();
    private int initialBufferSize;
    private int counter = 0;

    public abstract void onStats(List<JoinEvent> joinStats, List<TaskEvent> taskStats, int nextIndex, Flow flow);

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

        Join join = new Join(initialBufferSize, Executors.newSingleThreadExecutor(), (flowId + ":" + ++counter), JoinType.SINGLE_THREADED);
        join.init();
        flow.assignJoin(taskId, join);
    }

    public void addCachedThreadJoin(String flowId, String taskId) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = new Join(initialBufferSize, Executors.newCachedThreadPool(), (flowId + ":" + ++counter), JoinType.CACHED);
        join.init();
        flow.assignJoin(taskId, join);
    }

    public void addMultiThreadJoin(String flowId, String taskId, int threadCount) throws RuntimeException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = new Join(initialBufferSize, Executors.newFixedThreadPool(threadCount), (flowId + ":" + ++counter), JoinType.MULTI_THREADED);
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
