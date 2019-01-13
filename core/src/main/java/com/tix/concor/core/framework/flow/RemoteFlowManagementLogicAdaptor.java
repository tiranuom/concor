package com.tix.concor.core.framework.flow;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.ConfigureStatus;
import com.tix.concor.core.framework.JoinEvent;
import com.tix.concor.core.framework.JoinType;
import com.tix.concor.core.framework.TaskEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
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
        URL url = new URL("rmi", flowManagerConfig.getHost(), flowManagerConfig.getPort(), "RMIBasedRemoteFlowManagementLogic");
        Naming.rebind(url.toString(), this);
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
    public ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount) {
        return null;
    }

    @Override
    public ConfigureStatus removeJoin(String flowId, String taskId) {
        return null;
    }

    @Override
    public ConfigureStatus moveJoin(String flowId, String fromTask, String toTask) {
        return null;
    }


}
