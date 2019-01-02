package com.tix.concor.framework.task;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Continuation<B> {

    void continuing(Callable<B> callable) throws Throwable;
}
