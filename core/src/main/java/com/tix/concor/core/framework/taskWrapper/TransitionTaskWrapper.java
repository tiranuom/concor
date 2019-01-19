package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.task.TransitionTask;

public class TransitionTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private TransitionTask<A, B> task;

    public TransitionTaskWrapper(TransitionTask<A, B> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (context.isSuccessful()) {
            try {
                task.apply(a, callable -> nextTask.applyNext(callable.call(), context));
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
