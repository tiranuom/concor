package com.tix.concor.framework.task;

import com.tix.concor.framework.Context;

@FunctionalInterface
public interface TransitionTask<A, B> extends Task<A, B> {

    void apply(A a, Continuation<B> continuation) throws Throwable;
}
