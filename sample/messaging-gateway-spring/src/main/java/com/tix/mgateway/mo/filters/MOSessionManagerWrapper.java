package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SingleThreadedTask;
import com.tix.mgateway.SessionManager;
import com.tix.mgateway.mo.domain.MOMessage;

public class MOSessionManagerWrapper implements SingleThreadedTask<MOMessage, MOMessage> {

    private SessionManager sessionManager;

    public MOSessionManagerWrapper(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
        moMessage.setSession(sessionManager.getSession(moMessage.getFrom()));
        return moMessage;
    }

}
