package com.tix.concor.remote.services;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.*;

import javax.annotation.PostConstruct;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

public class RemoteManagementLogicWrapper implements RMIBasedRemoteFlowManagementLogic {

    private RMIBasedRemoteFlowManagementLogic remoteFlowManagementLogic;

    @PostConstruct
    public void init() throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(12099);
        remoteFlowManagementLogic = (RMIBasedRemoteFlowManagementLogic) registry.lookup("RMIBasedRemoteFlowManagementLogic");
    }

    private <B> B withReconnect(F<RMIBasedRemoteFlowManagementLogic, B> f) throws RemoteException {
        try {
            return f.apply(remoteFlowManagementLogic);
        } catch (ConnectException e) {
            try {
                init();
            } catch (NotBoundException e1) {
                e1.printStackTrace();
            }
            return f.apply(remoteFlowManagementLogic);
        }
    }

    @Override
    public Map<String, List<JoinEvent>> joinEvents() throws RemoteException {
        return withReconnect(RMIBasedRemoteFlowManagementLogic::joinEvents);
    }

    @Override
    public Map<String, List<TaskEvent>> taskEvents() throws RemoteException {
        return withReconnect(RMIBasedRemoteFlowManagementLogic::taskEvents);
    }

    @Override
    public Map<String, Integer> tps() throws RemoteException {
        return withReconnect(RMIBasedRemoteFlowManagementLogic::tps);
    }

    @Override
    public ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount, JoinTarget target) throws RemoteException {
        return withReconnect(rmiBasedRemoteFlowManagementLogic -> rmiBasedRemoteFlowManagementLogic.addJoin(flowId, taskId, joinType, threadCount, target));
    }

    @Override
    public ConfigureStatus removeJoin(String flowId, String taskId) throws RemoteException {
        return withReconnect(rmiBasedRemoteFlowManagementLogic -> rmiBasedRemoteFlowManagementLogic.removeJoin(flowId, taskId));
    }

    @Override
    public ConfigureStatus moveJoin(String flowId, String fromTask, String toTask) throws RemoteException {
        return withReconnect(rmiBasedRemoteFlowManagementLogic -> rmiBasedRemoteFlowManagementLogic.moveJoin(flowId, fromTask, toTask));
    }

    @Override
    public Map<String, FlowInfo> getSchema() throws RemoteException {
        return withReconnect(RMIBasedRemoteFlowManagementLogic::getSchema);
    }

    @FunctionalInterface
    private interface F<A, B> {
        B apply(A a) throws RemoteException;
    }

    @FunctionalInterface
    private interface SE<A> {
        void apply(A a) throws RemoteException;
    }

}
