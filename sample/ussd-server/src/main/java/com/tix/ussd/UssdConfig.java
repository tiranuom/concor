package com.tix.ussd;

import com.tix.concor.core.framework.Boot;
import com.tix.concor.core.framework.flow.FlowManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class UssdConfig {

    @Bean
    public UssdMessageSender ussdMessageSender() {
        return new UssdMessageSender();
    }

    @Bean
    @DependsOn("boot")
    public UssdFlowI ussdFlow() {
        return new UssdFlow();
    }

    @Bean
    Boot boot() {
        Boot boot = new Boot();
        boot.setFlowManagerConfig(new FlowManager.FlowManagerConfig());
        boot.init();
        return boot;
    }
}
