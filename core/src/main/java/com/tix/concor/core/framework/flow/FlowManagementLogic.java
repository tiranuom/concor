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

    public ConfigureStatus addSingleThreadJoin(String flowId, String taskId) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("could not find the flow");;

        Join join = new Join(initialBufferSize, Executors.newSingleThreadExecutor(), (flowId + ":" + ++counter), JoinType.SINGLE_THREADED);
        join.init();
        try {
            flow.assignJoin(taskId, join);
            return ConfigureStatus.success();
        } catch (RemoteException e) {
            join.destroy();
            e.printStackTrace();
            return ConfigureStatus.fail(e.getCause().getMessage());
        }
    }

    public ConfigureStatus addCachedThreadJoin(String flowId, String taskId) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("could not find the flow");;

        Join join = new Join(initialBufferSize, Executors.newCachedThreadPool(), (flowId + ":" + ++counter), JoinType.CACHED);
        join.init();
        try {
            flow.assignJoin(taskId, join);
            return ConfigureStatus.success();
        } catch (RemoteException e) {
            join.destroy();
            e.printStackTrace();
            return ConfigureStatus.fail(e.getCause().getMessage());
        }
    }

    public ConfigureStatus addMultiThreadJoin(String flowId, String taskId, int threadCount) throws RuntimeException, RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("could not find the flow");

        Join join = new Join(initialBufferSize, Executors.newFixedThreadPool(threadCount), (flowId + ":" + ++counter), JoinType.MULTI_THREADED);
        join.init();
        try {
            flow.assignJoin(taskId, join);
            return ConfigureStatus.success();
        } catch (RemoteException e) {
            join.destroy();
            e.printStackTrace();
            return ConfigureStatus.fail(e.getCause().getMessage());
        }
    }

    public ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount) throws RemoteException {
        switch (joinType) {
            case CACHED:
                return addCachedThreadJoin(flowId, taskId);
            case MULTI_THREADED:
                return addMultiThreadJoin(flowId, taskId, threadCount);
            case SINGLE_THREADED:
                return addSingleThreadJoin(flowId, taskId);
            default:
                return ConfigureStatus.fail("Unknown join type");
        }
    }

    public ConfigureStatus removeJoin(String flowId, String taskId) throws RuntimeException, RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("Could not find the flow");

        try {
            flow.assignJoin(taskId, null);
            return ConfigureStatus.success();
        } catch (RemoteException e) {
            return ConfigureStatus.fail(e.toString());
        }
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
