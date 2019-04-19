package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SingleThreadedTask;
import com.tix.mgateway.SessionManagerI;
import com.tix.mgateway.mo.domain.MOMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MOSessionManagerWrapper implements SingleThreadedTask<MOMessage, MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(MOSessionManagerWrapper.class);

    private SessionManagerI sessionManagerI;

    public MOSessionManagerWrapper(SessionManagerI sessionManagerI) {
        this.sessionManagerI = sessionManagerI;
    }

    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {

        logger.debug("Resolving session for [{}]", moMessage.getFrom());

        moMessage.setSession(sessionManagerI.getSession(moMessage.getFrom()));

        logger.debug("Session has been successfully resolved");
        return moMessage;
    }

}
