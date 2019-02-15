package com.tix.mgateway.mo;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import com.tix.mgateway.SessionManager;
import com.tix.mgateway.mo.filters.*;
import com.tix.mgateway.mo.filters.router.MORouter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MOFlow {

    private Flow<String> flow;

    @Autowired
    private MORouter moRouter;

    @Autowired
    private MOSessionManagerWrapper moSessionManagerWrapper;

    @Autowired
    private EndPointResolver endPointResolver;
    @Autowired
    private ATMessageSender atMessageSender;


    @PostConstruct
    public void init() {
        flow = Flows.<String>create("mo-flow")
                .map(new MOInTranslator(), "1")
                .mapSingleThreaded(moSessionManagerWrapper, "2")
                .map(moRouter, "3")
                .mapSynchronizedRemote(endPointResolver, "4")
                .map(new ATTranslator(), "5")
                .bind(atMessageSender, "6")
                .forEach(new MOTransLogger(), "7")
                .build();

    }
    
    public void apply(String moMessage) {
        flow.apply(moMessage);
    }
    
}
