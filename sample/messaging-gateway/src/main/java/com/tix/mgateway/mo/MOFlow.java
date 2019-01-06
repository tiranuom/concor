package com.tix.mgateway.mo;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import com.tix.mgateway.SessionManager;
import com.tix.mgateway.mo.filters.*;

public class MOFlow {

    private Flow<String> flow;

    public void init() {
        flow = Flows.<String>create("mo-flow")
                .map(new MOInTranslator())
                .mapSynchronized(new SessionManager())
                .map(new MORouter())
                .mapAsynchronous(new EndPointResolver())
                .map(new ATTranslator())
                .bind(new ATMessageSender())
                .forEach(new MOTransLogger())
                .build();

    }
    
    public void apply(String moMessage) {
        flow.apply(moMessage);
    }
    
}
