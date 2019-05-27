package com.tix.ussd;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class UssdFlow implements UssdFlowI {

    private static final Logger logger = LoggerFactory.getLogger(UssdFlow.class);

    @Autowired
    private UssdMessageSender messageSender;

    private Flow<String> flow;

    @PostConstruct
    public void init() {
        flow = Flows.<String>create("ussd-flow")
                .map(new UssdInTranslator()::apply, "translator")
                .map(new UssdSessionManager()::apply, "sessionManager")
                .forEach(new MenuStructureResolver()::apply, "menuStructureResolver")
                .forEach(new MenuResolver()::apply, "menuResolver")
                .bind(messageSender::send, "messageSender")
                .forEach(ussdSession -> logger.info("Ussd message sent [{}]", ussdSession.getMessage()))
                .catching(e -> {
                    logger.error("An error occurred", e);
                    return null;
                }).build();
    }

    @Override
    public void apply(String ussdMsg) {
        flow.apply(ussdMsg);
    }
}
