package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.Join;
import com.tix.concor.core.framework.task.Continuation;
import com.tix.concor.core.framework.task.TransitionTask;

import java.util.concurrent.Callable;

public class TransitionTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private TransitionTask<A, B> task;

    private Join continuationJoin = null;

    public TransitionTaskWrapper(TransitionTask<A, B> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (context.isSuccessful()) {
            try {
                task.apply(a, new Continuation<B>() {
                    @Override
                    public void continuing(Callable<B> callable) {
                        if (continuationJoin != null) {
                            continuationJoin.handle(() -> handleContinuation(callable), context);
                        } else {
                            handleContinuation(callable);
                        }
                    }

                    private void handleContinuation(Callable<B> callable) {
                        try {
                            nextTask.apply(callable.call(), context);
                        } catch (Exception e) {
                            context.setThrowable(e);
                            nextTask.apply(null, context);
                        }
                    }

                    @Override
                    public void onError(Callable<Throwable> callable) {
                        if (continuationJoin != null) {
                            continuationJoin.handle(() -> handleError(callable), context);
                        } else {
                            handleError(callable);
                        }
                    }

                    private void handleError(Callable<Throwable> callable) {
                        try {
                            context.setThrowable(callable.call());
                        } catch (Exception e) {
                            context.setThrowable(e);
                        }
                        nextTask.apply(null, context);
                    }
                });
            } catch (Throwable throwable) {
                context.setThrowable(throwable);
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(null, context);
        }
    }

    @Override
    protected String eventType() {
        return "asynchronous-remote";
    }
}
