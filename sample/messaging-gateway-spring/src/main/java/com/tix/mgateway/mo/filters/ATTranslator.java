package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class ATTranslator implements SimpleTask<MOMessage, MOMessage> {
    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
//        Thread.sleep(10);
        moMessage.setResponse(moMessage.getFrom() + "|" + moMessage.getTo() + "|" + moMessage.getMessage());
        return moMessage;
    }
}
