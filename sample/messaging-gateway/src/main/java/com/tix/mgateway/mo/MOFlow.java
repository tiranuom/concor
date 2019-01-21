package com.tix.mgateway.mo;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import com.tix.mgateway.SessionManager;
import com.tix.mgateway.mo.filters.*;

public class MOFlow {

    private Flow<String> flow;

    public void init() {
        flow = Flows.<String>create("mo-flow")
                .map(new MOInTranslator(), "1")
                .mapSingleThreaded(new SessionManager(), "2")
                .map(new MORouter(), "3")
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
