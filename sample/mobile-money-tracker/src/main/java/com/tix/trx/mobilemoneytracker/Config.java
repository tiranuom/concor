package com.tix.trx.mobilemoneytracker;

import com.tix.concor.core.framework.Boot;
import com.tix.concor.core.framework.flow.FlowManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class Config {

    @Bean
    public TrxManager trxManager() {
        return new TrxManager();
    }

    @Bean
    @DependsOn("boot")
    public TrxFlow trxFlow() {
        return new TrxFlow();
    }

    @Bean
    Boot boot() {
        Boot boot = new Boot();
        boot.setFlowManagerConfig(new FlowManager.FlowManagerConfig());
        boot.init();
        return boot;
    }

}
