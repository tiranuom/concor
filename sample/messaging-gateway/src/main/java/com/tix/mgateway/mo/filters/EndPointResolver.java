package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SynchronizedRemoteTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class EndPointResolver implements SynchronizedRemoteTask<MOMessage,MOMessage> {
    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
//        Thread.sleep(10);
        return moMessage;
    }
}
