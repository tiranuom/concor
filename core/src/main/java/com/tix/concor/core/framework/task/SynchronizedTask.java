package com.tix.concor.core.framework.task;

@FunctionalInterface
public interface SynchronizedTask<A, B> extends Task<A, B> {

    B apply(A a) throws Throwable;

}
