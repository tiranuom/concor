package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;

public class MOInTranslator implements SimpleTask<String, MOMessage> {

    @Override
    public MOMessage apply(String s) throws Throwable {
        String[] parts = s.split("|");
        return new MOMessage(parts[0], parts[1], parts[2]);
    }
}
