package com.tix.concor.core.framework.task;

@FunctionalInterface
public interface SingleThreadedTask<A, B> extends Task<A, B> {

    B apply(A a) throws Throwable;

}
