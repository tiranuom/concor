package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public abstract class FlowManagementLogic {

    private Map<String, Flow> flowMap = new HashMap<>();
    private int initialBufferSize;
    private int counter = 0;

    public abstract void onStats(List<JoinEvent> joinStats, List<TaskEvent> taskStats, int nextIndex, Flow flow);

    public void split(Flow flow, String taskId, Join join) throws RemoteException {
        if (flow != null) {
            flow.assignJoin(taskId, join);
        }
    }

    public Join join(Flow flow, String taskId) throws RemoteException {
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

    public void addMultiThreadJoin(String flowId, String taskId, int threadCount) throws RuntimeException, RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = new Join(initialBufferSize, Executors.newFixedThreadPool(threadCount), (flowId + ":" + ++counter), JoinType.MULTI_THREADED);
        join.init();
        flow.assignJoin(taskId, join);
    }

    public void removeJoin(String flowId, String taskId) throws RuntimeException, RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        flow.assignJoin(taskId, null);
    }

    public void moveJoin(String flowId, String fromTask, String toTask) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return;

        Join join = flow.assignJoin(fromTask, null);
        flow.assignJoin(toTask, join);
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

    public Map<String, FlowInfo> collectSchema() {
        return flowMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().collectSchema()));
    }
}
