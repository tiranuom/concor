package com.tix.concor.remote.config;

import com.tix.concor.common.RMIBasedRemoteFlowManagementLogic;
import com.tix.concor.remote.services.RemoteManagementLogicWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Configuration
public class ConcorConfiguration {

    @Bean
    RMIBasedRemoteFlowManagementLogic remoteFlowManagementLogic() throws RemoteException, NotBoundException {
        return new RemoteManagementLogicWrapper();
    }
}
