package com.tix.concor.core.framework.task;

@FunctionalInterface
public interface TransitionTask<A, B> extends Task<A, B> {

    void apply(A a, Continuation<B> continuation) throws Throwable;
}
