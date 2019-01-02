package com.tix.concor.common;

import com.tix.concor.common.JoinEvent;
import com.tix.concor.common.TaskEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RMIBasedRemoteFlowManagementLogic extends Remote {

    Map<String, List<JoinEvent>> joinEvents() throws RemoteException;

    Map<String, List<TaskEvent>> taskEvents() throws RemoteException;

}
