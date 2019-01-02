package com.tix.concor.framework.flow;

import com.tix.concor.framework.SniffingContext;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RMIBasedRemoteFlowManagementLogic extends Remote {

    Map<String, List<SniffingContext.JoinEvent>> joinEvents() throws RemoteException;

    Map<String, List<SniffingContext.TaskEvent>> taskEvents() throws RemoteException;

}
