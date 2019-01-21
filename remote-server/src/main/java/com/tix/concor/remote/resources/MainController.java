package com.tix.concor.remote.resources;

import com.tix.concor.core.framework.FlowInfo;
import com.tix.concor.core.framework.JoinEvent;
import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.TaskEvent;
import com.tix.concor.remote.domain.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class MainController {

    @Autowired
    private RMIBasedRemoteFlowManagementLogic service;

    @CrossOrigin
    @GetMapping("/tasks")
    public Mono<Map<String, List<TaskEvent>>> tasks() throws RemoteException {
        return Mono.just(service.taskEvents());
    }

    @CrossOrigin
    @GetMapping("/joins")
    public Mono<Map<String, List<JoinEvent>>> joins() throws RemoteException {
        return Mono.just(service.joinEvents());
    }

    @CrossOrigin
    @GetMapping("/stats")
    public Mono<Stats> stats() throws RemoteException {
        return Mono.just(new Stats(service.taskEvents(), service.joinEvents()));
    }

    @CrossOrigin
    @GetMapping("/schema")
    public Mono<Map<String, FlowInfo>> schema() throws RemoteException {
        return Mono.just(service.getSchema());
    }


}
