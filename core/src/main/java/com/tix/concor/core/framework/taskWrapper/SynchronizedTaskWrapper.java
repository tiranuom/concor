package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.task.SynchronizedTask;

public class SynchronizedTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private SynchronizedTask<A, B> task;

    public SynchronizedTaskWrapper(SynchronizedTask<A, B> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (context.isSuccessful()) {
            try {
                nextTask.applyNext(task.apply(a), context);
            } catch (Throwable throwable) {
                context.setThrowable(throwable);
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(null, context);
        }
    }
}
