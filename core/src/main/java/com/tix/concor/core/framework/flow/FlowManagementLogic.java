package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class FlowManagementLogic {

    private Map<String, Flow> flowMap = new HashMap<>();
    private int initialBufferSize;
    private int counter = 0;

    public abstract void onStats(List<JoinEvent> joinStats, List<TaskEvent> taskStats, int nextIndex, Flow flow);

    public ConfigureStatus addSingleThreadJoin(String flowId, String taskId, JoinTarget target) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("could not find the flow");;

        Join join = new Join(initialBufferSize, Executors.newSingleThreadExecutor(), (flowId + ":" + ++counter), JoinType.SINGLE_THREADED, target);
        join.init();
        return assignJoin(taskId, flow, join);
    }

    private ConfigureStatus assignJoin(String taskId, Flow flow, Join join) {
        try {
            flow.assignJoin(taskId, join);
            return ConfigureStatus.success();
        } catch (RemoteException e) {
            join.destroy();
            e.printStackTrace();
            return ConfigureStatus.fail(e.getCause().getMessage());
        }
    }

    public ConfigureStatus addCachedThreadJoin(String flowId, String taskId, JoinTarget target) throws RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("could not find the flow");;

        Join join = new Join(initialBufferSize, Executors.newCachedThreadPool(), (flowId + ":" + ++counter), JoinType.CACHED, target);
        join.init();
        return assignJoin(taskId, flow, join);
    }

    public ConfigureStatus addMultiThreadJoin(String flowId, String taskId, int threadCount, JoinTarget target) throws RuntimeException, RemoteException {
        Flow flow = flowMap.get(flowId);
        if (flow == null) return ConfigureStatus.fail("could not find the flow");

        Join join = new Join(initialBufferSize, Executors.newFixedThreadPool(threadCount), (flowId + ":" + ++counter), JoinType.MULTI_THREADED, target);
        join.init();
        return assignJoin(taskId, flow, join);
    }

    public ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount, JoinTarget target) throws RemoteException {
        switch (joinType) {
            case CACHED:
                return addCachedThreadJoin(flowId, taskId, target);
            case MULTI_THREADED:
                return addMultiThreadJoin(flowId, taskId, threadCount, target);
            case SINGLE_THREADED:
                return addSingleThreadJoin(flowId, taskId, target);
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

    public BiFunction<JoinType, JoinTarget, Join> getJoinGenerator() {
        return (joinType, target) -> {
            Join join;
            switch (joinType) {
                case SINGLE_THREADED:
                    join = new Join(initialBufferSize, Executors.newSingleThreadExecutor(), ("join" + ":" + ++counter), JoinType.SINGLE_THREADED, target);
                    break;
                case CACHED:
                    join =  new Join(initialBufferSize, Executors.newCachedThreadPool(), ("join" + ":" + ++counter), JoinType.CACHED, target);
                    break;
                default:
                    join = new Join(initialBufferSize, Executors.newFixedThreadPool(10), ("join" + ":" + ++counter), JoinType.MULTI_THREADED, target);
            }
            join.init();
            return join;
        };
    }
}
