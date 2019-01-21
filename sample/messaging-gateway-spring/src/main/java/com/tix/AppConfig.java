package com.tix;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.Boot;
import com.tix.concor.core.framework.flow.FlowManagementLogic;
import com.tix.concor.core.framework.flow.FlowManager;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogic;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogicAdaptor;
import com.tix.mgateway.mo.MOFlow;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.remoting.rmi.RmiServiceExporter;

import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    @Bean
    @DependsOn("boot")
    public MOFlow moFlow() {
        MOFlow moFlow = new MOFlow();
        moFlow.init();
        Executors.newScheduledThreadPool(100).scheduleAtFixedRate((Runnable) () -> moFlow.apply(""), 100, 100, TimeUnit.MICROSECONDS);
        return moFlow;
    }

    @Bean
    RMIBasedRemoteFlowManagementLogic remoteFlowManagementLogic() throws RemoteException {
        RemoteFlowManagementLogic flowManagementLogic = (RemoteFlowManagementLogic) FlowManager.getInstance().getFlowManagementLogic();
        return new RemoteFlowManagementLogicAdaptor(flowManagementLogic, new FlowManager.FlowManagerConfig());
    }

    @Bean
    Boot boot() {
        Boot boot = new Boot();
        boot.setFlowManagerConfig(new FlowManager.FlowManagerConfig());
        boot.init();
        return boot;
    }

//    @Bean
//    RmiServiceExporter exporter() throws RemoteException {
//        Class<RMIBasedRemoteFlowManagementLogic> flowManagementLogicClass = RMIBasedRemoteFlowManagementLogic.class;
//        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
//        rmiServiceExporter.setServiceInterface(flowManagementLogicClass);
//        rmiServiceExporter.setServiceName(flowManagementLogicClass.getSimpleName());
//        RemoteFlowManagementLogicAdaptor service = new RemoteFlowManagementLogicAdaptor(new RemoteFlowManagementLogic(), new FlowManager.FlowManagerConfig());
//        rmiServiceExporter.setService(service);
//        rmiServiceExporter.setRegistryPort(12000);
//        return rmiServiceExporter;
//    }
}