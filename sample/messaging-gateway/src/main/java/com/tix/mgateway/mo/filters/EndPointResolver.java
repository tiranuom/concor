package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.AsynchronousTask;
import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class EndPointResolver implements AsynchronousTask<MOMessage,MOMessage> {
    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
        return null;
    }
}
