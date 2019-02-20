package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SideEffect;
import com.tix.mgateway.mo.domain.MOMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MOTransLogger implements SideEffect<MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger("TRANS");

    @Override
    public void apply(MOMessage moMessage) throws Throwable {

        logger.info("AT|{}|{},{}|Success", moMessage.getFrom(), moMessage.getTo(), moMessage.getMessage());
    }
}
