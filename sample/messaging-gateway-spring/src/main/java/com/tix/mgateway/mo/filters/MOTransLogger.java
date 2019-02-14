package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SideEffect;
import com.tix.mgateway.mo.domain.MOMessage;

public class MOTransLogger implements SideEffect<MOMessage> {
    @Override
    public void apply(MOMessage moMessage) throws Throwable {
        System.out.println(Thread.currentThread().getName());
    }
}
