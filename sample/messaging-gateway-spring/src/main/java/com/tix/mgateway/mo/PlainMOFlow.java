package com.tix.mgateway.mo;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.mgateway.mo.domain.MOMessage;
import com.tix.mgateway.mo.filters.*;
import com.tix.mgateway.mo.filters.router.MORouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

public class PlainMOFlow implements MOFlowI {

    private static final Logger logger = LoggerFactory.getLogger(PlainMOFlow.class);

    @Autowired
    private MORouter moRouter;

    @Autowired
    private MOSessionManagerWrapper moSessionManagerWrapper;

    @Autowired
    private EndPointResolver endPointResolver;
    @Autowired
    private ATMessageSender atMessageSender;
    private MOInTranslator moInTranslator;
    private ATTranslator atTranslator;
    private MOTransLogger moTransLogger;

    public void init() {
        moInTranslator = new MOInTranslator();
        atTranslator = new ATTranslator();
        moTransLogger = new MOTransLogger();
    }

    public void apply(String moMessage) {
        MOMessage moMsg = null;
        try {
            moMsg = moInTranslator.apply(moMessage);
            moMsg = moSessionManagerWrapper.apply(moMsg);
            moMsg = moRouter.apply(moMsg);
            moMsg = endPointResolver.apply(moMsg);
            moMsg = atTranslator.apply(moMsg);
            MOMessage finalMoMsg = moMsg;
            moTransLogger.apply(finalMoMsg);
            atMessageSender.apply(moMsg, new Continuation<MOMessage>() {
                @Override
                public void continuing(Callable<MOMessage> callable) {
                    try {
                        moTransLogger.apply(finalMoMsg);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                @Override
                public void onError(Callable<Throwable> callable) {
                    try {
                        logger.error("An error occurred while executing the request", callable.call());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
