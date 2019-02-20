package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.task.SimpleTask;

public class SimpleTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private SimpleTask<A, B> task;

    public SimpleTaskWrapper(SimpleTask<A, B> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
        FlowTraceLog.trace("FUNCTIONAL|{}|{}|{}", id, context.getId(), a);
        if (context.isSuccessful()) {
            try {
                nextTask.apply(task.apply(a), context);
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
        return "simple";
    }
}
