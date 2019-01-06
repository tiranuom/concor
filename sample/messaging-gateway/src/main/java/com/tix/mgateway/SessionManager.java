package com.tix.mgateway;

import com.tix.concor.core.framework.task.SynchronizedTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class SessionManager implements SynchronizedTask<MOMessage,MOMessage> {
    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
        return null;
    }
}
