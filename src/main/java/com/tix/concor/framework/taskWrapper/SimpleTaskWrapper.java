package com.tix.concor.framework.taskWrapper;

import com.tix.concor.framework.Context;
import com.tix.concor.framework.task.SimpleTask;

public class SimpleTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private SimpleTask<A, B> task;

    public SimpleTaskWrapper(SimpleTask<A, B> task, String id) {
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
