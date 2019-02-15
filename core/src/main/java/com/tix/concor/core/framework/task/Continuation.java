package com.tix.concor.core.framework.task;

import javax.security.auth.callback.Callback;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Continuation<B> {

    void continuing(Callable<B> callable);

    void onError(Callable<Throwable> callable);
}
