package com.tix;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.Boot;
import com.tix.concor.core.framework.flow.FlowManager;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogic;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogicAdaptor;
import com.tix.mgateway.SessionManager;
import com.tix.mgateway.mo.MOFlow;
import com.tix.mgateway.mo.filters.router.MORouter;
import com.tix.mgateway.mo.filters.router.domain.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

@Configuration
public class AppConfig {

    @Bean
    @DependsOn("boot")
    public MOFlow moFlow() {
        MOFlow moFlow = new MOFlow();
        try {
            moFlow.init();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        Executors.newScheduledThreadPool(100).scheduleAtFixedRate((Runnable) () -> {
            try {
                moFlow.apply("");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, 100, 100, TimeUnit.MICROSECONDS);
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

    @Bean
    SessionManager sessionManager() {
        return new SessionManager(60000);
    }

    @Bean
    MORouter moRouter() {
        MORouter moRouter = new MORouter();
        moRouter.populate(Arrays.asList(
                new Route("070", "mobitel"),
                new Route("071", "mobitel"),
                new Route("072", "etisalat"),
                new Route("075", "airtel"),
                new Route("076", "dialog"),
                new Route("077", "dialog"),
                new Route("078", "hutch")
        ));
        return moRouter;
    }
}