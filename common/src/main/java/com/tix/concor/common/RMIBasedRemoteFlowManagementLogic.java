package com.tix.concor.common;

import com.tix.concor.core.framework.ConfigureStatus;
import com.tix.concor.core.framework.JoinEvent;
import com.tix.concor.core.framework.JoinType;
import com.tix.concor.core.framework.TaskEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RMIBasedRemoteFlowManagementLogic extends Remote {

    Map<String, List<JoinEvent>> joinEvents() throws RemoteException;

    Map<String, List<TaskEvent>> taskEvents() throws RemoteException;

    ConfigureStatus addJoin(String flowId, String taskId, JoinType joinType, int threadCount);

    ConfigureStatus removeJoin(String flowId, String taskId);

    ConfigureStatus moveJoin(String flowId, String fromTask, String toTask);
}
