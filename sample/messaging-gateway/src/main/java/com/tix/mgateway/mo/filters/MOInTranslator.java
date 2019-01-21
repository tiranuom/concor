package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class MOInTranslator implements SimpleTask<String, MOMessage> {

    @Override
    public MOMessage apply(String s) throws Throwable {
//        Thread.sleep(10);
        return new MOMessage();
    }
}
