package com.tix.mgateway.mo;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import com.tix.mgateway.SessionManager;
import com.tix.mgateway.mo.filters.*;
import com.tix.mgateway.mo.filters.router.MORouter;
import org.springframework.beans.factory.annotation.Autowired;

public class MOFlow {

    private Flow<String> flow;

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private MORouter moRouter;


    public void init() {
        flow = Flows.<String>create("mo-flow")
                .map(new MOInTranslator(), "1")
                .mapSingleThreaded(new MOSessionManagerWrapper(sessionManager), "2")
                .map(moRouter, "3")
                .mapSynchronizedRemote(new EndPointResolver(), "4")
                .map(new ATTranslator(), "5")
                .bind(new ATMessageSender(), "6")
                .forEach(new MOTransLogger(), "7")
                .build();

    }
    
    public void apply(String moMessage) {
        flow.apply(moMessage);
    }
    
}
