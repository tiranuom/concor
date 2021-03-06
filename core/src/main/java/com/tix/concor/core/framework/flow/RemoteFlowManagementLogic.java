package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.JoinEvent;
import com.tix.concor.core.framework.TaskEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class RemoteFlowManagementLogic extends FlowManagementLogic {

    Map<String, Map<Integer, List<JoinEvent>>> joinStatMap = new ConcurrentHashMap<>();
    Map<String, Map<Integer, List<TaskEvent>>> taskStatMap = new ConcurrentHashMap<>();
    Map<String, Integer> tpsMap = new ConcurrentHashMap<>();

    @Override
    public void onStats(List<JoinEvent> joinStats, List<TaskEvent> taskStats, int nextIndex, Flow flow) {
        String id = flow.getId();
        Map<Integer, List<JoinEvent>> joinStatsMap = joinStatMap.computeIfAbsent(id, k -> new ConcurrentHashMap<>());
        joinStatsMap.put(nextIndex, joinStats);

        Map<Integer, List<TaskEvent>> taskStatsMap = taskStatMap.computeIfAbsent(id, k -> new ConcurrentHashMap<>());
        taskStatsMap.put(nextIndex, taskStats);

        tpsMap.put(id, flow.getTps());
    }

    public Map<String, List<JoinEvent>> joinEvents() throws RemoteException {
        Map<String, List<JoinEvent>> results = joinStatMap.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> {

            HashMap<String, List<JoinEvent>> merged = new HashMap<>();

            for (List<JoinEvent> list : e.getValue().values()) {
                for (JoinEvent joinEvent : list) {
                    merged.computeIfAbsent(joinEvent.getId(), a -> new ArrayList<>()).add(joinEvent);
                }
            }

            return merged.values().stream().map(JoinEvent::merge).collect(Collectors.toList());
        }));
//        joinStatMap.clear();
        return results;
    }

    public Map<String, List<TaskEvent>> taskEvents() throws RemoteException {
        Map<String, List<TaskEvent>> result = taskStatMap.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> {

            HashMap<String, List<TaskEvent>> merged = new HashMap<>();

            e.getValue().values().forEach(taskEvents -> {
                taskEvents.forEach(taskEvent -> {
                    merged.computeIfAbsent(taskEvent.getId(), a -> new ArrayList<>()).add(taskEvent);
                });
            });

            return merged.values().stream().map(TaskEvent::merge).collect(Collectors.toList());
        }));
//        taskStatMap.clear();
        return result;
    }

    public Map<String, Integer> getTps() {
        HashMap<String, Integer> results = new HashMap<>();
        tpsMap.forEach(results::put);
//        results.clear();
        return results;
    }

}
