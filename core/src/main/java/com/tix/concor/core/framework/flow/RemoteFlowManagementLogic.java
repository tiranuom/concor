package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.JoinEvent;
import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.TaskEvent;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toMap;

public class RemoteFlowManagementLogic extends FlowManagementLogic {

    Map<Flow, Map<Integer, List<JoinEvent>>> joinStatMap = new ConcurrentHashMap<>();
    Map<Flow, Map<Integer, List<TaskEvent>>> taskStatMap = new ConcurrentHashMap<>();

    @Override
    public void onStats(List<JoinEvent> joinStats, List<TaskEvent> taskStats, int nextIndex, Flow flow) {
        Map<Integer, List<JoinEvent>> joinStatsMap = joinStatMap.computeIfAbsent(flow, k -> new ConcurrentHashMap<>());
        joinStatsMap.put(nextIndex, joinStats);

        Map<Integer, List<TaskEvent>> taskStatsMap = taskStatMap.computeIfAbsent(flow, k -> new ConcurrentHashMap<>());
        taskStatsMap.put(nextIndex, taskStats);
    }

    public Map<String, List<JoinEvent>> joinEvents() throws RemoteException {
        return joinStatMap.entrySet().stream().collect(toMap(e -> e.getKey().getId(), e -> {

            HashMap<String, List<JoinEvent>> merged = new HashMap<>();

            for (List<JoinEvent> list : e.getValue().values()) {
                for (JoinEvent joinEvent : list) {
                    merged.computeIfAbsent(joinEvent.getId(), a -> new ArrayList<>()).add(joinEvent);
                }
            }

            return merged.values().stream().map(JoinEvent::merge).collect(Collectors.toList());
        }));
    }

    public Map<String, List<TaskEvent>> taskEvents() throws RemoteException {
        return taskStatMap.entrySet().stream().collect(toMap(e -> e.getKey().getId(), e -> {

            HashMap<String, List<TaskEvent>> merged = new HashMap<>();

            e.getValue().values().forEach(taskEvents -> {
                taskEvents.forEach(taskEvent -> {
                    merged.computeIfAbsent(taskEvent.getId(), a -> new ArrayList<>()).add(taskEvent);
                });
            });

            return merged.values().stream().map(TaskEvent::merge).collect(Collectors.toList());
        }));
    }

}
