package com.tix.concor.core.framework.flow;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class RemoteFlowManagementLogicAdaptor extends UnicastRemoteObject implements RMIBasedRemoteFlowManagementLogic {

    private RemoteFlowManagementLogic remoteFlowManagementLogic;
    private FlowManager.FlowManagerConfig flowManagerConfig;

    public RemoteFlowManagementLogicAdaptor(RemoteFlowManagementLogic remoteFlowManagementLogic, FlowManager.FlowManagerConfig flowManagerConfig) throws RemoteException {
        this.remoteFlowManagementLogic = remoteFlowManagementLogic;
        this.flowManagerConfig = flowManagerConfig;
    }

    public void init() throws MalformedURLException, RemoteException {
        LocateRegistry.createRegistry(flowManagerConfig.getPort());
        String url = "rmi://" + flowManagerConfig.getHost() + ":" + flowManagerConfig.getPort() + "/RMIBasedRemoteFlowManagementLogic";
        System.out.println(url);
        Naming.rebind(url, this);
    }

    @Override
    public Map<String, List<JoinEvent>> joinEvents() throws RemoteException {
        return remoteFlowManagementLogic.joinEvents();
    }

    @Override
    public Map<String, List<TaskEvent>> taskEvents() throws RemoteException {
        return remoteFlowManagementLogic.taskEvents();
    }

    @Override
    public Map<String, Integer> tps() throws RemoteException {
        return remoteFlowManagementLogic.getTps();
    }

    @Override
    public ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount, JoinTarget target) throws RemoteException {
        return remoteFlowManagementLogic.addJoin(flowId, taskId, joinType, threadCount, target);
    }

    @Override
    public ConfigureStatus removeJoin(String flowId, String taskId) throws RemoteException {
        return remoteFlowManagementLogic.removeJoin(flowId, taskId);
    }

    @Override
    public ConfigureStatus moveJoin(String flowId, String fromTask, String toTask) throws RemoteException {
        return null;
    }

    @Override
    public Map<String, FlowInfo> getSchema() throws RemoteException {
        return remoteFlowManagementLogic.collectSchema();
    }


}
