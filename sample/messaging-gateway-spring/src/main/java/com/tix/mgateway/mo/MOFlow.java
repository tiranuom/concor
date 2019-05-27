package com.tix.mgateway.mo;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import com.tix.mgateway.mo.filters.*;
import com.tix.mgateway.mo.filters.router.MORouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MOFlow implements MOFlowI {

    private static final Logger logger = LoggerFactory.getLogger(MOFlow.class);

    private Flow<String> flow;

    @Autowired
    private MORouter moRouter;

    @Autowired
    private MOSessionManagerWrapper moSessionManagerWrapper;

    @Autowired
    private EndPointResolver endPointResolver;
    @Autowired
    private ATMessageSender atMessageSender;


    @Override
    @PostConstruct
    public void init() {
        flow = Flows.<String>create("mo-flow")
                .map(new MOInTranslator(), "translator")
                .mapSingleThreaded(moSessionManagerWrapper, "session Manager")
                .map(moRouter, "Router")
                .mapSynchronizedRemote(endPointResolver, "End Point Resolver")
                .map(new ATTranslator(), "AT translator")
                .bind(atMessageSender, "Message sender")
                .forEach(new MOTransLogger(), "trans logger")
                .catching(new Catching(), "Error Handler")
                .build();

    }

    @Override
    public void apply(String moMessage) {
        logger.debug("MO Message received.");
        flow.apply(moMessage);
    }

}
