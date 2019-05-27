package com.tix.trx.mobilemoneytracker;

import com.tix.concor.core.framework.flow.Flow;
import com.tix.concor.core.framework.flow.Flows;
import com.tix.concor.core.framework.task.CatchTask;
import com.tix.trx.mobilemoneytracker.domain.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class TrxFlow implements TrxFlowI {

    private static final Logger logger = LoggerFactory.getLogger(TrxFlow.class);

    @Autowired
    private TrxManager trxManager;
    private Flow<String> flow;

    @PostConstruct
    public void init() {
        flow = Flows.<String>create("trx-flow")
                .map(new Translator()::apply, "translator")
                .mapSynchronizedRemote(trxManager::read, "trx-reader")
                .forEach(new TrxCalculator()::apply, "calculate")
                .mapSynchronizedRemote(trxManager::update, "trx-updater")
                .forEach(request -> logger.info(request.getStatus()))
                .catching(e -> {
                    logger.error("An error occurred", e);
                    return null;
                })
                .build();
    }

    @Override
    public void apply(String message) {
        this.flow.apply(message);
    }
}
