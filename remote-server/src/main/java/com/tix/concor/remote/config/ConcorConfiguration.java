package com.tix.concor.remote.config;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ConcorConfiguration {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:12000/RMIBasedRemoteFlowManagementLogic");
        rmiProxyFactoryBean.setServiceInterface(RMIBasedRemoteFlowManagementLogic.class);
        return rmiProxyFactoryBean;
    }
}
