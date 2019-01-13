package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.ConfigurationException;
import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.JoinType;
import com.tix.concor.core.framework.task.SynchronizedRemoteTask;

public class SynchronizedRemoteTaskWrapper<A, B> extends TaskWrapper<A, B> {
    private final SynchronizedRemoteTask<A, B> task;

    public SynchronizedRemoteTaskWrapper(SynchronizedRemoteTask<A, B> task, String id) {
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

    @Override
    protected void assertJoinAssigbility(JoinType joinType) throws ConfigurationException {
        if (joinType == JoinType.SINGLE_THREADED) throw new ConfigurationException("", this.id);
        super.assertJoinAssigbility(joinType);
    }
}
