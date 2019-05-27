package com.tix.ussd;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.ussd.domain.UssdMessage;
import com.tix.ussd.domain.UssdSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;

public class PlainUssdFlow implements UssdFlowI {

    private static final Logger logger = LoggerFactory.getLogger(PlainUssdFlow.class);

    private UssdInTranslator ussdInTranslator;
    private UssdSessionManager ussdSessionManager;
    private MenuStructureResolver menuStructureResolver;
    private MenuResolver menuResolver;

    @Autowired
    private UssdMessageSender messageSender;

    @PostConstruct
    public void init() {
        ussdInTranslator = new UssdInTranslator();
        ussdSessionManager = new UssdSessionManager();
        menuStructureResolver = new MenuStructureResolver();
        menuResolver = new MenuResolver();
    }

    @Override
    public void apply(String ussdMsg) {
        try {
            UssdMessage ussdMessage = ussdInTranslator.apply(ussdMsg);
            UssdSession ussdSession = ussdSessionManager.apply(ussdMessage);
            menuStructureResolver.apply(ussdSession);
            menuResolver.apply(ussdSession);
            messageSender.send(ussdSession, new Continuation<UssdSession>() {
                @Override
                public void continuing(Callable<UssdSession> callable) {
                    logger.info("Ussd message sent [{}]", ussdSession.getMessage());
                }

                @Override
                public void onError(Callable<Throwable> callable) {
                    try {
                        logger.error("An error occurred", callable.call());
                    } catch (Exception e) {
                        logger.error("An error occurred", e);
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
