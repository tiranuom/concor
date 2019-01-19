package com.tix.concor.common;

import com.tix.concor.core.framework.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RMIBasedRemoteFlowManagementLogic extends Remote {

    Map<String, List<JoinEvent>> joinEvents() throws RemoteException;

    Map<String, List<TaskEvent>> taskEvents() throws RemoteException;

    ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount) throws RemoteException;

    ConfigureStatus removeJoin(String flowId, String taskId) throws RemoteException;

    ConfigureStatus moveJoin(String flowId, String fromTask, String toTask) throws RemoteException;

    Map<String, FlowInfo> getSchema() throws RemoteException;
}
