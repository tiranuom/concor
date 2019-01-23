package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.ConfigurationException;
import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.JoinType;
import com.tix.concor.core.framework.task.SingleThreadedTask;

public class SingleThreadedTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private SingleThreadedTask<A, B> task;

    public SingleThreadedTaskWrapper(SingleThreadedTask<A, B> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
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
    protected void assertLocalJoinAssignability(JoinType joinType) throws ConfigurationException {
        if (joinType != JoinType.SINGLE_THREADED) throw new ConfigurationException("Single Threaded task should not be catered with a multithreaded thread pool.", this.id);
    }

    @Override
    protected String eventType() {
        return "single-threaded";
    }
}
