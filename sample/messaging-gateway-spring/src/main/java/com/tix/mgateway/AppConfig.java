package com.tix.mgateway;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.core.framework.Boot;
import com.tix.concor.core.framework.flow.FlowManager;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogic;
import com.tix.concor.core.framework.flow.RemoteFlowManagementLogicAdaptor;
import com.tix.mgateway.mo.MOFlow;
import com.tix.mgateway.mo.MOFlowI;
import com.tix.mgateway.mo.PlainMOFlow;
import com.tix.mgateway.mo.filters.ATMessageSender;
import com.tix.mgateway.mo.filters.EndPointResolver;
import com.tix.mgateway.mo.filters.MOSessionManagerWrapper;
import com.tix.mgateway.mo.filters.router.MORouter;
import com.tix.mgateway.mo.filters.router.domain.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.rmi.RemoteException;
import java.util.Arrays;

//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

@Configuration
public class AppConfig {

    @Bean
    SessionManagerI sessionManager() {
        return new SimpleSessionManager();
    }

    @Bean
    MOSessionManagerWrapper moSessionManagerWrapper(SessionManagerI sessionManagerI) {
        return new MOSessionManagerWrapper(sessionManagerI);
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

    @Bean
    EndPointResolver endPointResolver() {
        return new EndPointResolver();
    }

    @Bean
    @DependsOn("boot")
    public MOFlowI moFlow() {
//        return new PlainMOFlow();
        return new MOFlow();
    }


    @Bean
    RMIBasedRemoteFlowManagementLogic remoteFlowManagementLogic() throws RemoteException {
        RemoteFlowManagementLogic flowManagementLogic = (RemoteFlowManagementLogic) FlowManager.getInstance().getFlowManagementLogic();
        return new RemoteFlowManagementLogicAdaptor(flowManagementLogic, new FlowManager.FlowManagerConfig());
    }

    @Bean
    ATMessageSender atMessageSender() {
        return new ATMessageSender();
    }

    @Bean
    Boot boot() {
        Boot boot = new Boot();
        boot.setFlowManagerConfig(new FlowManager.FlowManagerConfig());
        boot.init();
        return boot;
    }
}