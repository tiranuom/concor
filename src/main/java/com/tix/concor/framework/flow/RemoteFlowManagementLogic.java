package com.tix.concor.framework.flow;

import com.tix.concor.framework.SniffingContext;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toMap;

public class RemoteFlowManagementLogic extends FlowManagementLogic implements RMIBasedRemoteFlowManagementLogic {

    Map<Flow, Map<Integer, List<SniffingContext.JoinEvent>>> joinStatMap = new HashMap<>();
    Map<Flow, Map<Integer, List<SniffingContext.TaskEvent>>> taskStatMap = new HashMap<>();

    @Override
    public void onStats(List<SniffingContext.JoinEvent> joinStats, List<SniffingContext.TaskEvent> taskStats, int nextIndex, Flow flow) {
        Map<Integer, List<SniffingContext.JoinEvent>> joinStatsMap = joinStatMap.computeIfAbsent(flow, k -> new HashMap<>());
        joinStatsMap.put(nextIndex, joinStats);

        Map<Integer, List<SniffingContext.TaskEvent>> taskStatsMap = taskStatMap.computeIfAbsent(flow, k -> new HashMap<>());
        taskStatsMap.put(nextIndex, taskStats);
    }

    public Map<String, List<SniffingContext.JoinEvent>> joinEvents() throws RemoteException {
        return joinStatMap.entrySet().stream().collect(toMap(e -> e.getKey().getId(), e -> {

            HashMap<String, List<SniffingContext.JoinEvent>> merged = new HashMap<>();

            for (List<SniffingContext.JoinEvent> list : e.getValue().values()) {
                for (SniffingContext.JoinEvent joinEvent : list) {
                    merged.computeIfAbsent(joinEvent.getId(), a -> new ArrayList<>()).add(joinEvent);
                }
            }

            return merged.values().stream().map(SniffingContext.JoinEvent::merge).collect(Collectors.toList());
        }));
    }

    public Map<String, List<SniffingContext.TaskEvent>> taskEvents() throws RemoteException {
        return taskStatMap.entrySet().stream().collect(toMap(e -> e.getKey().getId(), e -> {

            HashMap<String, List<SniffingContext.TaskEvent>> merged = new HashMap<>();

            for (List<SniffingContext.TaskEvent> list : e.getValue().values()) {
                for (SniffingContext.TaskEvent joinEvent : list) {
                    merged.computeIfAbsent(joinEvent.getId(), a -> new ArrayList<>()).add(joinEvent);
                }
            }

            return merged.values().stream().map(SniffingContext.TaskEvent::merge).collect(Collectors.toList());
        }));
    }

}
