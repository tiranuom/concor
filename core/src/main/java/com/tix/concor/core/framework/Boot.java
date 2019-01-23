package com.tix.concor.core.framework;

import com.tix.concor.core.framework.flow.FlowManager;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogic;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogicAdaptor;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class Boot {

    private FlowManager.FlowManagerConfig flowManagerConfig;

    public void init() {
        reload();
    }

    public void reload() {
        try {
            FlowManager.getInstance().reconfigure(flowManagerConfig);
            RemoteFlowManagementLogic remoteFlowManagementLogic = new RemoteFlowManagementLogic();
            remoteFlowManagementLogic.setInitialBufferSize(flowManagerConfig.getBufferSize());
            RemoteFlowManagementLogicAdaptor adaptor = new RemoteFlowManagementLogicAdaptor(remoteFlowManagementLogic, flowManagerConfig);
            adaptor.init();
            FlowManager.getInstance().setFlowManagementLogic(remoteFlowManagementLogic);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setFlowManagerConfig(FlowManager.FlowManagerConfig flowManagerConfig) {
        this.flowManagerConfig = flowManagerConfig;
    }
}
