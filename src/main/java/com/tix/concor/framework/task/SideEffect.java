package com.tix.concor.framework.task;

@FunctionalInterface
public interface SideEffect<A> extends Task<A, Void>{

    public void apply(A a) throws Throwable;

}
