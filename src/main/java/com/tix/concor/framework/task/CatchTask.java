package com.tix.concor.framework.task;

public interface CatchTask<A> extends Task<A, A> {

    A onError(Throwable e) throws Throwable;
}
