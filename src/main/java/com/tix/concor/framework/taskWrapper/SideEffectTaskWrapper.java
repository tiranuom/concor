package com.tix.concor.framework.taskWrapper;

import com.tix.concor.framework.Context;
import com.tix.concor.framework.task.SideEffect;

public class SideEffectTaskWrapper<A> extends TaskWrapper<A, A> {

    private SideEffect<A> task;
    public SideEffectTaskWrapper(SideEffect<A> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (context.isSuccessful()) {
            try {
                task.apply(a);
                nextTask.apply(a, context);
            } catch (Throwable throwable) {
                context.setThrowable(throwable);
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(null, context);
        }
    }
}
