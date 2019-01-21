package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.concor.core.framework.task.TransitionTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class ATMessageSender implements TransitionTask<MOMessage,MOMessage> {
    @Override
    public void apply(MOMessage moMessage, Continuation<MOMessage> continuation) throws Throwable {
//        Thread.sleep(10);
        continuation.continuing(() -> moMessage);
    }
}
